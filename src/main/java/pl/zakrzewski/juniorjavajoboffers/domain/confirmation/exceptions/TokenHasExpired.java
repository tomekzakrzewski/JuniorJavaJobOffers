package pl.zakrzewski.juniorjavajoboffers.domain.confirmation.exceptions;

public class TokenHasExpired extends RuntimeException {
    public TokenHasExpired(String token) {
        super("Token " + token + " has expired");
    }
}
