package pl.zakrzewski.juniorjavajoboffers.domain.register.token;

import java.util.Optional;

public interface ConfirmationTokenRepository {
    Optional<ConfirmationToken> findByToken(String token);

}
