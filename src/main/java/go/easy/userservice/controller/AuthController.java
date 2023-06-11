package go.easy.userservice.controller;

import go.easy.userservice.dto.CreateUserRequest;
import go.easy.userservice.dto.verification.SignInRequest;
import go.easy.userservice.dto.verification.SignInResponse;
import go.easy.userservice.dto.verification.ValidateTokenRequest;
import go.easy.userservice.dto.verification.ValidateTokenResponse;
import go.easy.userservice.service.auth.AuthService;
import go.easy.userservice.service.auth.JwtService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;
    private final JwtService jwtService;

    @Operation(summary = "регистрация пользователя")
    @PostMapping("/signUp")
    ResponseEntity<String> signUp(@RequestBody @Valid CreateUserRequest createUserRequest) {
        authService.signUp(createUserRequest);
        return ResponseEntity.ok("Success");
    }

    @Operation(summary = "аутентификация пользователя")
    @PostMapping("/signIn")
    ResponseEntity<SignInResponse> signIn(@RequestBody @Valid SignInRequest signInRequest) {
        return ResponseEntity.ok(authService.signIn(signInRequest));
    }

    @Operation(summary = "повторная отправка кода")
    @PutMapping("/otp/send/{email}")
    ResponseEntity<String> resendOtp(@PathVariable String email) {
        authService.sendOtp(email);
        return ResponseEntity.ok("Success");
    }

    @Operation(summary = "валидация токена")
    @PostMapping("/token/validate")
    ResponseEntity<ValidateTokenResponse> validateToken(@RequestBody @Valid ValidateTokenRequest request) {
        return ResponseEntity.ok(jwtService.validateToken(request));

    }
}
