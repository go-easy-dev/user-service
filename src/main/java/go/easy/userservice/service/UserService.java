package go.easy.userservice.service;

import go.easy.userservice.dto.CreateUserRequest;
import go.easy.userservice.dto.UpdateEmailRequest;
import go.easy.userservice.dto.UserProfile;
import go.easy.userservice.exception.UserAlreadyExistException;
import go.easy.userservice.exception.UserNotFoundException;
import go.easy.userservice.mapper.UserMapper;
import go.easy.userservice.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public UserProfile getUserByEmail(String email) {
        log.info("trying to get user by email {}", email);
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new UserNotFoundException(HttpStatus.NOT_FOUND, "can't find user by email: " + email));
    }

    public UserProfile getUserById(String userId) {
        log.info("trying to get user by id: {}", userId);
        return userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException(HttpStatus.NOT_FOUND, "can't find user by id: " + userId));
    }

    public UserProfile updateUserEmail(UpdateEmailRequest updateEmailRequest) {
        log.info("updating user email: {}", updateEmailRequest);
        return userRepository.findById(updateEmailRequest.userId())
                .map(userProfile -> userProfile.toBuilder()
                        .email(updateEmailRequest.email())
                        .build())
                .map(userRepository::save)
                .orElseThrow(() -> new UserNotFoundException(HttpStatus.NOT_FOUND, "can't find user by email: " + updateEmailRequest.email()));
    }


    public UserProfile createUser(CreateUserRequest request) {
        log.info("saving user: {}", request);
        checkUserExist(request.getEmail());

        var user = UserMapper.INSTANCE.map(request);
        return userRepository.save(user);
    }

    private void checkUserExist(String email) {
        userRepository.findByEmail(email)
                .ifPresent(user -> {
                    throw new UserAlreadyExistException(HttpStatus.BAD_REQUEST, "user already exist: " + user.getId());
                });
    }


}
