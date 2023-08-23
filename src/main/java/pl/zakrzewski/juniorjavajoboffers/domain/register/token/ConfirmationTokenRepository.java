package pl.zakrzewski.juniorjavajoboffers.domain.register.token;

import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.Optional;

public interface ConfirmationTokenRepository extends JpaRepository<ConfirmationToken, String> {
    Optional<ConfirmationToken> findByToken(String token);

    void updateConfirmedAt(String token, LocalDateTime now);
}
