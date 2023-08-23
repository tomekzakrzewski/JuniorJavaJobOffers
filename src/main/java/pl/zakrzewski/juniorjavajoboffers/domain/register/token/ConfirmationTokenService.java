package pl.zakrzewski.juniorjavajoboffers.domain.register.token;

import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;
import java.util.Optional;

@RequiredArgsConstructor
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


}
