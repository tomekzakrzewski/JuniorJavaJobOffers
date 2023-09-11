package pl.zakrzewski.juniorjavajoboffers.domain.emailsender.exceptions;

public class OffersNotFound extends RuntimeException {
    public OffersNotFound() {
        super("New offers not found");
    }
}
