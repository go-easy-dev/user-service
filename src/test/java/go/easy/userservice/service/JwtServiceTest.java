package go.easy.userservice.service;

import go.easy.userservice.dto.verification.ValidateTokenRequest;
import go.easy.userservice.dto.verification.ValidateTokenResponse;
import go.easy.userservice.service.auth.JwtService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.UUID;

public class JwtServiceTest {

    private final JwtService service = new JwtService();

    @Test
    void should_generate_token() {
        // given
        var userId = UUID.randomUUID().toString();

        // when
        var token = service.generateToken(userId);

        // then
        Assertions.assertThat(token)
                .isNotBlank();
    }

    @Test
    void should_get_user_id_from_token() {
        // given
        var userId = UUID.randomUUID().toString();
        var token = service.generateToken(userId);

        // when
        var actual = service.getUserIdFromToken(token);

        // then
        Assertions.assertThat(actual)
                .isNotBlank()
                .isEqualTo(userId);
    }

    @Test
    void should_validate_token() {
        // given
        var token = service.generateToken(UUID.randomUUID().toString());
        var request = new ValidateTokenRequest(token);
        // when
        var actual = service.validateToken(request);

        // then
        Assertions.assertThat(actual)
                .matches(ValidateTokenResponse::isValid);
    }
}
