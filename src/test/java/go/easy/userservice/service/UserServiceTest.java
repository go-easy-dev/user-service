package go.easy.userservice.service;

import go.easy.userservice.dto.CreateUserRequest;
import go.easy.userservice.dto.Gender;
import go.easy.userservice.dto.UpdateEmailRequest;
import go.easy.userservice.dto.UserProfile;
import go.easy.userservice.exception.UserAlreadyExistException;
import go.easy.userservice.exception.UserNotFoundException;
import go.easy.userservice.repository.UserRepository;
import org.assertj.core.api.Assertions;
import org.jeasy.random.EasyRandom;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.argThat;

@ExtendWith(SpringExtension.class)
public class UserServiceTest {
    private final EasyRandom random = new EasyRandom();
    @Mock
    private UserRepository userRepository;
    @InjectMocks
    private UserService userService;

    @Test
    void should_give_profile_by_id() {
        // given
        Mockito.when(userRepository.findById(Mockito.anyString())).thenReturn(
                Optional.of(random.nextObject(UserProfile.class)));

        // when
        var actual = userService.getUserById("id");

        // then
        Assertions.assertThat(actual).isNotNull();
    }

    @Test
    void should_not_give_profile_by_id() {
        // given
        Mockito.when(userRepository.findById(Mockito.anyString())).thenReturn(
                Optional.empty());

        // then
        Assertions.assertThatThrownBy(() -> userService.getUserById("id"))
                .isExactlyInstanceOf(UserNotFoundException.class)
                .hasMessage("404 NOT_FOUND \"can't find user by id: id\"")
                .hasFieldOrPropertyWithValue("status", HttpStatus.NOT_FOUND);
    }

    @Test
    void should_give_profile_by_email() {
        // given
        Mockito.when(userRepository.findByEmail(Mockito.anyString())).thenReturn(
                Optional.of(random.nextObject(UserProfile.class)));

        // when
        var actual = userService.getUserByEmail("email");

        // then
        Assertions.assertThat(actual).isNotNull();
    }

    @Test
    void should_not_give_profile_by_email() {
        // given
        Mockito.when(userRepository.findById(Mockito.anyString())).thenReturn(
                Optional.empty());

        // then
        Assertions.assertThatThrownBy(() -> userService.getUserByEmail("email"))
                .isExactlyInstanceOf(UserNotFoundException.class)
                .hasMessage("404 NOT_FOUND \"can't find user by email: email\"")
                .hasFieldOrPropertyWithValue("status", HttpStatus.NOT_FOUND);
    }

    @Test
    void should_update_email() {
        // given
        Mockito.when(userRepository.findById(Mockito.anyString())).thenReturn(
                Optional.of(random.nextObject(UserProfile.class)));
        Mockito.when(userRepository.save(Mockito.any())).thenReturn(
                random.nextObject(UserProfile.class));

        // when
        userService.updateUserEmail(getUpdateRequest());

        // then
        Mockito.verify(userRepository, Mockito.times(1))
                .save(argThat(user -> user.getEmail().equals("new_email@google.com")));
    }


    @Test
    void should_not_update_email() {
        // given
        Mockito.when(userRepository.findById(Mockito.anyString())).thenReturn(
                Optional.empty());

        // then
        Assertions.assertThatThrownBy(() -> userService.updateUserEmail(getUpdateRequest()))
                .isExactlyInstanceOf(UserNotFoundException.class)
                .hasMessage("404 NOT_FOUND \"can't find user by email: new_email@google.com\"")
                .hasFieldOrPropertyWithValue("status", HttpStatus.NOT_FOUND);
    }

    @Test
    void should_not_create_user() {
        // given
        Mockito.when(userRepository.findByEmail(Mockito.anyString())).thenReturn(
                Optional.of(random.nextObject(UserProfile.class).toBuilder()
                        .id("USER_ID")
                        .build()));


        // then
        Assertions.assertThatThrownBy(() -> userService.createUser(getCreateRequest()))
                .isExactlyInstanceOf(UserAlreadyExistException.class)
                .hasMessage("400 BAD_REQUEST \"user already exist: USER_ID\"")
                .hasFieldOrPropertyWithValue("status", HttpStatus.BAD_REQUEST);
    }

    @Test
    void should_create_user() {
        // given
        Mockito.when(userRepository.findByEmail(Mockito.anyString())).thenReturn(
                Optional.empty());

        // when
        userService.createUser(getCreateRequest());

        // then
        Mockito.verify(userRepository, Mockito.times(1))
                .save(argThat(user -> {
                    Assertions.assertThat(user)
                            .hasFieldOrPropertyWithValue("email", "email@google.com")
                            .hasFieldOrPropertyWithValue("firstName", "name")
                            .hasFieldOrPropertyWithValue("gender", Gender.MALE);
                    return true;
                }));
    }

    private UpdateEmailRequest getUpdateRequest() {
        return new UpdateEmailRequest("USER_ID", "new_email@google.com");
    }

    private CreateUserRequest getCreateRequest() {
        return new CreateUserRequest("name", "email@google.com", Gender.MALE);
    }
}
