package pl.zakrzewski.juniorjavajoboffers.domain.register.token;

import lombok.AllArgsConstructor;
import pl.zakrzewski.juniorjavajoboffers.domain.register.User;
import pl.zakrzewski.juniorjavajoboffers.domain.register.dto.ConfirmationTokenResultDto;
import pl.zakrzewski.juniorjavajoboffers.domain.register.exceptions.TokenAlreadyConfirmed;
import pl.zakrzewski.juniorjavajoboffers.domain.register.exceptions.TokenHasExpired;
import pl.zakrzewski.juniorjavajoboffers.domain.register.exceptions.TokenNotFoundException;

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

    public ConfirmationTokenResultDto confirmToken(String token) {
        ConfirmationToken confirmationToken = repository.findByToken(token)
                        .orElseThrow(() -> new TokenNotFoundException(token));
        if (confirmationToken.getConfirmedAt() != null) {
            throw new TokenAlreadyConfirmed(token);
        }
        LocalDateTime expiredAt = confirmationToken.getExpiresAt();
        if (expiredAt.isBefore(LocalDateTime.now())) {
            throw new TokenHasExpired(token);
        }
        repository.updateConfirmedAt(token, LocalDateTime.now());
        return new ConfirmationTokenResultDto(token, LocalDateTime.now());
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
