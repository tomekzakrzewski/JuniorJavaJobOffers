package pl.zakrzewski.juniorjavajoboffers.domain.confirmation;

import org.junit.jupiter.api.Test;
import pl.zakrzewski.juniorjavajoboffers.domain.confirmation.dto.ConfirmationTokenResultDto;
import pl.zakrzewski.juniorjavajoboffers.domain.confirmation.exceptions.TokenAlreadyConfirmed;
import pl.zakrzewski.juniorjavajoboffers.domain.confirmation.exceptions.TokenHasExpired;
import pl.zakrzewski.juniorjavajoboffers.domain.confirmation.exceptions.TokenNotFoundException;
import pl.zakrzewski.juniorjavajoboffers.domain.register.User;

import java.time.LocalDateTime;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class ConfirmationFacadeTest {
    ConfirmationFacade confirmationFacade = new ConfirmationFacade(new InMemoryConfirmationTokenRepository());

    @Test
    void should_generate_token_with_given_user() {
        User user = User.builder()
                .username("asd")
                .email("asd@gmail.com")
                .id(UUID.randomUUID().toString())
                .build();

        ConfirmationToken confirmationToken = confirmationFacade.generateConfirmationToken(user);

        assertThat(confirmationToken.getUser()).isEqualTo(user);
    }

    @Test
    void should_save_confirmation_token() {
        User user = User.builder()
                .username("asd")
                .email("asd@gmail.com")
                .id(UUID.randomUUID().toString())
                .build();

        ConfirmationToken confirmationToken = confirmationFacade.generateConfirmationToken(user);
        confirmationFacade.saveConfirmationToken(confirmationToken);
        ConfirmationToken savedToken = confirmationFacade.getConfirmationTokenByToken(confirmationToken.getToken());

        assertThat(savedToken.equals(confirmationToken));
    }

    @Test
    void should_delete_token_by_user_id() {
        User user = User.builder()
                .username("asd")
                .email("asd@gmail.com")
                .id(UUID.randomUUID().toString())
                .build();

        ConfirmationToken confirmationToken = confirmationFacade.generateConfirmationToken(user);
        confirmationFacade.saveConfirmationToken(confirmationToken);
        String token = confirmationToken.getToken();

        confirmationFacade.deleteConfirmationTokenByUserId(user.getId());
        assertThrows(TokenNotFoundException.class, () -> confirmationFacade.getConfirmationTokenByToken(token));
    }

    @Test
    void should_get_confirmation_token_by_token() {
        User user = User.builder()
                .username("asd")
                .email("asd@gmail.com")
                .id(UUID.randomUUID().toString())
                .build();
        ConfirmationToken confirmationToken = confirmationFacade.generateConfirmationToken(user);
        confirmationFacade.saveConfirmationToken(confirmationToken);
        ConfirmationToken savedToken = confirmationFacade.getConfirmationTokenByToken(confirmationToken.getToken());

        assertThat(savedToken.equals(confirmationToken));
    }

    @Test
    void should_throw_exception_when_token_not_found() {
        assertThrows(TokenNotFoundException.class,
                () -> confirmationFacade.getConfirmationTokenByToken(UUID.randomUUID().toString()));
    }

    @Test
    void should_set_token_confirmed_at_now_when_confirmed() {
        User user = User.builder()
                .username("asd")
                .email("asd@gmail.com")
                .id(UUID.randomUUID().toString())
                .build();
        ConfirmationToken confirmationToken = confirmationFacade.generateConfirmationToken(user);
        ConfirmationToken saved = confirmationFacade.saveConfirmationToken(confirmationToken);
        ConfirmationTokenResultDto result = confirmationFacade.confirmToken(saved.getToken());
        LocalDateTime confirmedAt = result.getConfirmedAt();
        assertThat(confirmedAt.equals(LocalDateTime.now()));
    }

    @Test
    void should_throw_exception_when_token_already_confirmed() {
        User user = User.builder()
                .username("asd")
                .email("asd@gmail.com")
                .id(UUID.randomUUID().toString())
                .build();
        ConfirmationToken confirmationToken = confirmationFacade.generateConfirmationToken(user);
        confirmationFacade.saveConfirmationToken(confirmationToken);
        confirmationFacade.confirmToken(confirmationToken.getToken());
        assertThrows(TokenAlreadyConfirmed.class, () -> confirmationFacade.confirmToken(confirmationToken.getToken()));
    }

    @Test
    void should_throw_exception_when_token_expired() {
        ConfirmationToken confirmationToken = ConfirmationToken.builder()
                .token(UUID.randomUUID().toString())
                .expiresAt(LocalDateTime.now().minusDays(1))
                .build();

        confirmationFacade.saveConfirmationToken(confirmationToken);

        assertThrows(TokenHasExpired.class, () -> confirmationFacade.confirmToken(confirmationToken.getToken()));
    }
}
