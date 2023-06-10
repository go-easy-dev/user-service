package go.easy.userservice.service.auth;

import go.easy.userservice.dto.CreateUserRequest;
import go.easy.userservice.dto.verification.SignInRequest;
import go.easy.userservice.dto.verification.SignInResponse;
import go.easy.userservice.exception.VerificationException;
import go.easy.userservice.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserService userService;
    private final VerificationService verificationService;
    private final NotificationSenderService notificationSenderService;

    private final JwtService jwtService;

    private final String SIGN_UP_TEXT = """
            Здравствуйте!
            Мы рады, что вы решили присоединиться к Легко. После регистрации вы попадете в свое пространство, где сможете пройти тестирование и получить доступ к карте развития.
            Ваш код подтверждения: %s
                         
            С уважением,
            Команда Легко;""";

    private final String SIGN_IN_TEXT = """
            Здравствуйте!
            Мы рады снова видеть вас в Легко. После авторизации вы попадете в свое пространство, где сможете пройти тестирование и получить доступ к карте развития.\s
                         
            Ваш код подтверждения: %s
                         
            С уважением,
            Команда Легко""";

    public void signUp(CreateUserRequest request) {
        log.info("sing up user: {}", request);
        var user = userService.createUser(request);
        var otp = verificationService.createEmailVerificationOtp(user.getId(), user.getEmail());
        var text = String.format(SIGN_UP_TEXT, otp);
        notificationSenderService.sendEmail(user.getEmail(), otp, text);
        log.info("verification code successfully sent");
    }

    public SignInResponse signIn(SignInRequest request) {
        log.info("trying sign in user: {}", request);

        var user = userService.getUserByEmail(request.getUserEmail());
        var verification = verificationService.checkVerification(user.getId(),
                request.getUserEmail(),
                request.getOtp());
        log.info("verification is: {}", verification);

        if (verification.isPresent()) {
            verificationService.confirmVerification(verification.get().getId());
            return SignInResponse.builder()
                    .userId(user.getId())
                    .accessToken(jwtService.generateToken(user.getId()))
                    .build();
        } else {
            throw new VerificationException(HttpStatus.UNAUTHORIZED, "request can't be authorized: " + request);
        }
    }

    public void sendOtp(String email) {
        log.info("sending otp to user: {}", email);
        var user = userService.getUserByEmail(email);
        var otp = verificationService.createEmailVerificationOtp(user.getId(), user.getEmail());
        var text = String.format(SIGN_IN_TEXT, otp);

        notificationSenderService.sendEmail(user.getEmail(), otp, text);
    }
}
