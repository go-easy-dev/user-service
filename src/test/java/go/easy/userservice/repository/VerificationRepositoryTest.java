package go.easy.userservice.repository;

import go.easy.userservice.dto.verification.VerificationEntity;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;

@DataMongoTest
class VerificationRepositoryTest {
    @Autowired
    private VerificationRepository verificationRepository;

    @BeforeEach
    void init() {
        verificationRepository.save(VerificationEntity.builder()
                .id("TEST_ID")
                .verificationCode("123456")
                .userId("USER_ID")
                .build());
    }

    @Test
    void should_find_by_id() {
        // when
        var actual = verificationRepository.findById("TEST_ID");

        // then
        Assertions.assertThat(actual)
                .isPresent()
                .map(VerificationEntity::getId)
                .get()
                .isEqualTo("TEST_ID");
    }

    @Test
    void should_find_by_email() {
        // when
        var actual = verificationRepository.findByUserIdAndVerificationCode("USER_ID", "123456");

        // then
        Assertions.assertThat(actual)
                .isPresent()
                .map(VerificationEntity::getId)
                .get()
                .isEqualTo("TEST_ID");
    }
}
