package go.easy.userservice.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class UserAlreadyExistException extends ResponseStatusException {
    public UserAlreadyExistException(HttpStatus status, String errorMessage) {
        super(status, errorMessage);
    }

}
