package pl.zakrzewski.juniorjavajoboffers.domain.register.token;

import pl.zakrzewski.juniorjavajoboffers.domain.register.User;

import java.time.LocalDateTime;

public class ConfirmationToken {
    private String id;
    private String token;
    private LocalDateTime createdAt;
    private LocalDateTime confirmedAt;
    private User user;

}
