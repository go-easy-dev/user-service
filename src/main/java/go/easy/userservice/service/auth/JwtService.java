package go.easy.userservice.service.auth;

import go.easy.userservice.dto.verification.ValidateTokenRequest;
import go.easy.userservice.dto.verification.ValidateTokenResponse;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.time.Instant;
import java.util.Base64;
import java.util.Date;

@Slf4j
@Service
public class JwtService {

    private static final String SECRET_KEY = "STloj9a+gvYEf8F258ltffCfrHIX42DCkmqEEGszL8bhOKC3YyR5atOm6fk1zL7zPsQBkJszwSIp9lmDBCBNXA==";
    private static final long EXPIRATION_TIME = 604800000; // 24 часа

    public String generateToken(String userId) {
        log.info("generating token for user: {}", userId);
        Date now = new Date();
        Date expirationDate = new Date(now.getTime() + EXPIRATION_TIME);

        return Jwts.builder()
                .setSubject(userId)
                .setIssuedAt(Date.from(Instant.now()))
                .setExpiration(expirationDate)
                .claim("ROLE", "User")
                .signWith(generateKey())
                .compact();
    }

    private Key generateKey() {
        var decodedKey = Base64.getDecoder().decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(decodedKey);
    }

    public String getUserIdFromToken(String token) {
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(generateKey())
                .build()
                .parseClaimsJws(token)
                .getBody();

        return claims.getSubject();
    }

    public ValidateTokenResponse validateToken(ValidateTokenRequest request) {
        var response = ValidateTokenResponse.builder();
        try {
            return response.valid(parseClaims(request.getTokenValue())
                            .get("ROLE")
                            .equals("User"))
                    .build();

        } catch (Exception ex) {
            return response
                    .valid(false)
                    .build();
        }
    }

    private Claims parseClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(generateKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }
}

