package go.easy.userservice.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Value;

@Value
public class CreateUserRequest {

    @NotBlank
    String firstName;
    @NotBlank
    String email;
    @NotNull
    Gender gender;

}
