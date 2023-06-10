package go.easy.userservice.dto.verification;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SignInResponse {

    @NotBlank
    private String userId;
    @NotBlank
    private String accessToken;
}
