package go.easy.userservice.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class VerificationException extends ResponseStatusException {
    public VerificationException(HttpStatus status, String errorMessage) {
        super(status, errorMessage);
    }

}
