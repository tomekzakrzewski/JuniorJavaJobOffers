package pl.zakrzewski.juniorjavajoboffers.domain.register.token;

import lombok.*;
import pl.zakrzewski.juniorjavajoboffers.domain.register.User;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@Builder
public class ConfirmationToken {
    private String id;
    private String token;
    private LocalDateTime createdAt;
    private LocalDateTime confirmedAt;
    private LocalDateTime expiresAt;
    private User user;

//    public ConfirmationToken(String token, LocalDateTime createdAt, LocalDateTime expiresAt, User user) {
//        this.token = token;
//        this.createdAt = createdAt;
//        this.expiresAt = expiresAt;
//        this.user = user;
//    }
}
