package go.easy.userservice.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class UserNotFoundException extends ResponseStatusException {
	public UserNotFoundException(HttpStatus status, String errorMessage) {
		super(status, errorMessage);
	}

}
