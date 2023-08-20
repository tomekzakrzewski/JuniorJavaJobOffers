package pl.zakrzewski.juniorjavajoboffers.domain.register.exceptions;

import lombok.Getter;

@Getter
public class UserAlreadyExistException extends RuntimeException {

    public UserAlreadyExistException(String email) {
        super("User with email " + email + " is already subscribed");
    }
}
