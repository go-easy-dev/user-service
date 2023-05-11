package go.easy.userservice.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;


public record UpdateEmailRequest(@NotBlank String userId, @Email @NotBlank String email) {
}
