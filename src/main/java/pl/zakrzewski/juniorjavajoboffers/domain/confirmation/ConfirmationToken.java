package pl.zakrzewski.juniorjavajoboffers.domain.confirmation;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.UuidGenerator;
import org.springframework.data.annotation.CreatedDate;
import pl.zakrzewski.juniorjavajoboffers.domain.register.User;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@Entity
@Table(name = "confirmations")
@NoArgsConstructor
@AllArgsConstructor
public class ConfirmationToken {
    @Id
    @UuidGenerator
    private String id;
    private String token;
    @CreatedDate
    private LocalDateTime createdAt;
    private LocalDateTime confirmedAt;
    private LocalDateTime expiresAt;
    @ManyToOne(targetEntity = User.class)
    @JoinColumn(nullable = false, name = "user_id")
    private User user;
}
