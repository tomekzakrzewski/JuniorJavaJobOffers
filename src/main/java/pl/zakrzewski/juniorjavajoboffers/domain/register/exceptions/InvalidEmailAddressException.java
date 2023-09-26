package pl.zakrzewski.juniorjavajoboffers.domain.register.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.BAD_REQUEST)
public class InvalidEmailAddressException extends RuntimeException {
    public InvalidEmailAddressException(String email) {
        super("Invalid email address: " + email);
    }

}
