package go.easy.userservice.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Value;

@Value
public class UpdateUserEmailRequest {

    @NotBlank
    String userId;

    @NotBlank
    @Email
    String email;
}
