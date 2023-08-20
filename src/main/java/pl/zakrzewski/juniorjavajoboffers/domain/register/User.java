package pl.zakrzewski.juniorjavajoboffers.domain.register;

import lombok.*;
import org.springframework.scheduling.annotation.EnableAsync;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Setter
@Getter
public class User {
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
