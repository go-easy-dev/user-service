package go.easy.userservice.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Value;

@Value
public class CreateUserRequest {

    @NotBlank String firstName;
    @NotBlank String email;
    @NotBlank Gender gender;

}
