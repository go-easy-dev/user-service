package go.easy.userservice.service;

import go.easy.userservice.dto.verification.VerificationEntity;
import go.easy.userservice.dto.verification.VerificationStatus;
import go.easy.userservice.dto.verification.VerificationType;
import go.easy.userservice.repository.VerificationRepository;
import go.easy.userservice.service.auth.VerificationService;
import org.jeasy.random.EasyRandom;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class VerificationServiceTest {
    private final EasyRandom random = new EasyRandom();
    @Mock
    VerificationRepository verificationRepository;
    @InjectMocks
    private VerificationService service;

    @Test
    void should_create_verification() {
        // given
        Mockito.when(verificationRepository.save(Mockito.any(VerificationEntity.class))).
                thenReturn(VerificationEntity.builder().build());

        // when
        service.createEmailVerificationOtp("USER_ID", "email");

        //then
        Mockito.verify(verificationRepository).save(Mockito.argThat(entity -> {
            Assertions.assertEquals("USER_ID", entity.getUserId());
            Assertions.assertEquals("email", entity.getVerificationProvider());
            Assertions.assertEquals(VerificationType.EMAIL, entity.getVerificationType());
            Assertions.assertEquals(6, entity.getVerificationCode().length());

            return true;
        }));
    }

    @Test
    void should_check_verification() {
        // given
        Mockito.when(verificationRepository.findByUserIdAndVerificationCode(Mockito.any(), Mockito.any()))
                .thenReturn(Optional.of(VerificationEntity.builder()
                        .verificationProvider("email")
                        .status(VerificationStatus.NOT_VERIFIED)
                        .expiryDate(LocalDateTime.now().plusDays(1))
                        .build())
                );

        // when
        var actual = service.checkVerification("USER_ID", "email", "123456");

        //then
        Assertions.assertTrue(actual.isPresent());
    }

    @Test
    void should_verify_verification() {
        // given
        Mockito.when(verificationRepository.findById(Mockito.any()))
                .thenReturn(Optional.of(VerificationEntity.builder()
                        .verificationProvider("email")
                        .status(VerificationStatus.NOT_VERIFIED)
                        .expiryDate(LocalDateTime.now().plusDays(1))
                        .build())
                );

        Mockito.when(verificationRepository.save(Mockito.any(VerificationEntity.class))).
                thenReturn(VerificationEntity.builder().build());

        // when
        service.confirmVerification("ID");
        //then
        Mockito.verify(verificationRepository).save(Mockito.argThat(entity -> VerificationStatus.VERIFIED == entity.getStatus()));
    }
}
