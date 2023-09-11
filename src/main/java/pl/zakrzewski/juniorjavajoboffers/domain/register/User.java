package pl.zakrzewski.juniorjavajoboffers.domain.register;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.UuidGenerator;
import org.springframework.scheduling.annotation.EnableAsync;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Setter
@Getter
@Entity
@Table(name = "users")
public class User {
    @Id
    @UuidGenerator
    private String id;
    private String username;
    private String email;
    private boolean enabled;

    public User(String username, String email) {
        this.username = username;
        this.email = email;
        this.enabled = false;
    }
}
