package go.easy.userservice.service.auth;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class NotificationSenderService {
    private final JavaMailSender mailSender;

    public void sendEmail(String email, String otp, String text) {
        log.info("sending otp to user email: {}", email);

        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(email);
        message.setSubject("Код авторизации");
        message.setText(text);
        message.setFrom("robot@goeasy.space");

        mailSender.send(message);
    }
}
