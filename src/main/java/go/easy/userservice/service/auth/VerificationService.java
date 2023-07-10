package go.easy.userservice.service.auth;

import go.easy.userservice.dto.verification.VerificationEntity;
import go.easy.userservice.dto.verification.VerificationStatus;
import go.easy.userservice.dto.verification.VerificationType;
import go.easy.userservice.exception.VerificationException;
import go.easy.userservice.repository.VerificationRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Random;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class VerificationService {
    private final Random random = new Random();
    private final VerificationRepository repository;

    public String createEmailVerificationOtp(String userId, String email) {
        log.info("creating verification for user: {}", userId);

        var verification = VerificationEntity.builder()
                .id(UUID.randomUUID().toString())
                .userId(userId)
                .verificationType(VerificationType.EMAIL)
                .status(VerificationStatus.NOT_VERIFIED)
                .verificationCode(createCode())
                .verificationProvider(email)
                .createdDate(LocalDateTime.now())
                .expiryDate(LocalDateTime.now().plusDays(1))
                .build();
        return repository.save(verification).getVerificationCode();
    }

    public Optional<VerificationEntity> checkVerification(String userId, String email, String otp) {
        log.info("try to find verification for user: {}", userId);
        return repository.findByUserIdAndVerificationCode(userId, otp)
                .filter(verification -> verification.getVerificationProvider().equals(email.toLowerCase()))
                .filter(verification -> verification.getStatus() == VerificationStatus.NOT_VERIFIED)
                .filter(verification -> verification.getExpiryDate().isAfter(LocalDateTime.now()));
    }

    public void confirmVerification(String verificationId) {
        log.info("confirming verification with id: {}", verificationId);
        repository.findById(verificationId)
                .map(verification -> verification.toBuilder()
                        .status(VerificationStatus.VERIFIED)
                        .confirmedDate(LocalDateTime.now())
                        .build())
                .map(repository::save)
                .orElseThrow(() -> new VerificationException(HttpStatus.NOT_FOUND, "can't find verification by id: " + verificationId));
    }

    private String createCode() {
        int otpLength = 6;
        int minOtpValue = 100000;
        int maxOtpValue = 999999;

        int otp = random.nextInt(maxOtpValue - minOtpValue + 1) + minOtpValue;

        return String.format("%0" + otpLength + "d", otp);
    }
}
