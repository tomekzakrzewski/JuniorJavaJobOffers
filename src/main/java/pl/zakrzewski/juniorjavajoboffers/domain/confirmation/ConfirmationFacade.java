package pl.zakrzewski.juniorjavajoboffers.domain.confirmation;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import pl.zakrzewski.juniorjavajoboffers.domain.confirmation.dto.ConfirmationTokenResultDto;
import pl.zakrzewski.juniorjavajoboffers.domain.confirmation.exceptions.TokenAlreadyConfirmed;
import pl.zakrzewski.juniorjavajoboffers.domain.confirmation.exceptions.TokenHasExpired;
import pl.zakrzewski.juniorjavajoboffers.domain.confirmation.exceptions.TokenNotFoundException;
import pl.zakrzewski.juniorjavajoboffers.domain.register.User;

import java.time.LocalDateTime;
import java.util.UUID;

@Component
@AllArgsConstructor
public class ConfirmationFacade {
    private final ConfirmationTokenRepository confirmationTokenRepository;

    public ConfirmationToken generateConfirmationToken(User user) {
        return ConfirmationToken.builder()
                .token(UUID.randomUUID().toString())
                .createdAt(LocalDateTime.now())
                .expiresAt(LocalDateTime.now().plusMinutes(30))
                .user(user)
                .build();
    }

    public ConfirmationToken saveConfirmationToken(ConfirmationToken confirmationToken) {
        return confirmationTokenRepository.save(confirmationToken);
    }

    public void deleteConfirmationTokenByUserId(String userId) {
        confirmationTokenRepository.deleteConfirmationTokenByUser_Id(userId);
    }

    public ConfirmationToken getConfirmationTokenByToken(String token) {
        return confirmationTokenRepository.findByToken(token)
                .orElseThrow(() -> new TokenNotFoundException(token));
    }

    public ConfirmationTokenResultDto confirmToken(String token) {
        ConfirmationToken confirmationToken = getConfirmationTokenByToken(token);
        if (isTokenConfirmed(confirmationToken))
            throw new TokenAlreadyConfirmed(token);
        if (isTokenExpired(confirmationToken))
            throw new TokenHasExpired(token);

        LocalDateTime confirmedAt = LocalDateTime.now();
        confirmationTokenRepository.updateConfirmedAt(token, confirmedAt);
        return new ConfirmationTokenResultDto(token, confirmedAt);
    }

    private boolean isTokenConfirmed(ConfirmationToken confirmationToken) {
        return confirmationToken.getConfirmedAt() != null;
    }

    private boolean isTokenExpired(ConfirmationToken confirmationToken) {
        LocalDateTime expiredAt = confirmationToken.getExpiresAt();
        if (expiredAt.isBefore(LocalDateTime.now())) {
            return true;
        }
        return false;
    }
}
