package pl.zakrzewski.juniorjavajoboffers.domain.register.token;

import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import pl.zakrzewski.juniorjavajoboffers.domain.register.User;
import pl.zakrzewski.juniorjavajoboffers.domain.register.dto.ConfirmationTokenDto;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@AllArgsConstructor
public class ConfirmationTokenService {

    private ConfirmationTokenRepository repository;

    public ConfirmationToken saveConfirmationToken(ConfirmationToken confirmationToken) {
        return repository.save(confirmationToken);
    }

    public Optional<ConfirmationToken> getToken(String token) {
        return repository.findByToken(token);
    }

    public void setConfirmedAt(String token) {
        repository.updateConfirmedAt(token, LocalDateTime.now());
    }

    public ConfirmationToken generateConfirmationToken(User user) {
        return ConfirmationToken.builder()
                .token(UUID.randomUUID().toString())
                .createdAt(LocalDateTime.now())
                .expiresAt(LocalDateTime.now().plusMinutes(30))
                .user(user)
                .build();

    }


}
