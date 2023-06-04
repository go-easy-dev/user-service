package go.easy.userservice.dto.verification;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ValidateTokenResponse {
    private boolean valid;
}
