package pl.zakrzewski.juniorjavajoboffers.domain.register.exceptions;

public class TokenNotFoundException extends RuntimeException {
    public TokenNotFoundException(String token) {
        super("Token " + token + " not found");
    }
}
