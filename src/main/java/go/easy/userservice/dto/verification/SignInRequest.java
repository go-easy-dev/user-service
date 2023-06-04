package go.easy.userservice.dto.verification;

import jakarta.validation.constraints.NotBlank;
import lombok.Value;

@Value
public class SignInRequest {

    @NotBlank
    String userEmail;
    @NotBlank
    String otp;
}
