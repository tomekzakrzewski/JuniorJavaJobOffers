package pl.zakrzewski.juniorjavajoboffers.domain.emailsender.exceptions;

public class ConfirmedUsersNotFound extends RuntimeException {
    public ConfirmedUsersNotFound() {
        super("Cant find users with activated account");
    }
}
