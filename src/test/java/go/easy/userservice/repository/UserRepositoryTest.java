package go.easy.userservice.repository;

import go.easy.userservice.dto.UserProfile;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;

@DataMongoTest
class UserRepositoryTest {
    @Autowired
    private UserRepository userRepository;

    @BeforeEach
    void init() {
        userRepository.save(UserProfile.builder()
                .id("TEST_ID")
                .firstName("NAME")
                .email("email@google.com")
                .build());
    }

    @Test
    void should_find_by_id() {
        // when
        var actual = userRepository.findById("TEST_ID");

        // then
        Assertions.assertThat(actual)
                .isPresent()
                .map(UserProfile::getId)
                .get()
                .isEqualTo("TEST_ID");
    }

    @Test
    void should_find_by_email() {
        // when
        var actual = userRepository.findByEmail("email@google.com");

        // then
        Assertions.assertThat(actual)
                .isPresent()
                .map(UserProfile::getEmail)
                .get()
                .isEqualTo("email@google.com");
    }
}
