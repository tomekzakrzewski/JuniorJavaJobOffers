package pl.zakrzewski.juniorjavajoboffers.domain.confirmation.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import pl.zakrzewski.juniorjavajoboffers.domain.register.User;

import java.time.LocalDateTime;

@Builder
@Getter
@Setter
public class ConfirmationTokenDto {
    private String id;
    private String token;
    private LocalDateTime createdAt;
    private LocalDateTime confirmedAt;
    private LocalDateTime expiresAt;
    private User user;
}
