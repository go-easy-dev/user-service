package go.easy.userservice.dto.verification;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Data
@Document(collection = "verification")
@Builder(toBuilder = true)
public class VerificationEntity {

    @Id
    private String id;

    @NotBlank
    private String userId;

    @NotBlank
    private String verificationProvider;

    @NotNull
    private VerificationType verificationType;

    @NotBlank
    private String verificationCode;

    @NotNull
    private VerificationStatus status;

    private LocalDateTime createdDate;

    private LocalDateTime confirmedDate;

    private LocalDateTime expiryDate;
}
