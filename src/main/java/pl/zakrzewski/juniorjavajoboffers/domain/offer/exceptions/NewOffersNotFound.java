package pl.zakrzewski.juniorjavajoboffers.domain.offer.exceptions;

public class NewOffersNotFound extends RuntimeException {
    public NewOffersNotFound() {
        super("New offers not found");
    }
}
