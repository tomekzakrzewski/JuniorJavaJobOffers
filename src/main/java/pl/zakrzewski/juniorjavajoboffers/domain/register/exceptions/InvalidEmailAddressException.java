package pl.zakrzewski.juniorjavajoboffers.domain.register.exceptions;

public class InvalidEmailAddressException extends RuntimeException {
    public InvalidEmailAddressException(String email) {
        super("Invalid email address: " + email);
    }

}
