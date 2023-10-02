package pl.zakrzewski.juniorjavajoboffers.domain.register.token;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import pl.zakrzewski.juniorjavajoboffers.domain.register.User;
import pl.zakrzewski.juniorjavajoboffers.domain.register.dto.ConfirmationTokenResultDto;
import pl.zakrzewski.juniorjavajoboffers.domain.register.dto.UserDto;
import pl.zakrzewski.juniorjavajoboffers.domain.register.exceptions.TokenAlreadyConfirmed;
import pl.zakrzewski.juniorjavajoboffers.domain.register.exceptions.TokenHasExpired;
import pl.zakrzewski.juniorjavajoboffers.domain.register.exceptions.TokenNotFoundException;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@AllArgsConstructor
@Service
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
        if (isTokenConfirmed(confirmationToken)) {
            throw new TokenAlreadyConfirmed(token);
        }
        if (isTokenExpired(confirmationToken)) {
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

    public void deleteConfirmationTokenByUserId(String userId) {
        repository.deleteConfirmationTokenByUser_Id(userId);
    }

    private boolean isTokenConfirmed(ConfirmationToken confirmationToken) {
        if (confirmationToken.getConfirmedAt() != null) {
            return true;
        }
        return false;
    }

    private boolean isTokenExpired(ConfirmationToken confirmationToken) {
        LocalDateTime expiredAt = confirmationToken.getExpiresAt();
        if (expiredAt.isBefore(LocalDateTime.now())) {
            return true;
        }
        return false;
    }



}
