package pl.zakrzewski.juniorjavajoboffers.domain.register.exceptions;

public class TokenAlreadyConfirmed extends RuntimeException {
    public TokenAlreadyConfirmed(String token) {
        super("Token " + token + " has already been confirmed");
    }
}
