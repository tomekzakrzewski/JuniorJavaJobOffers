package pl.zakrzewski.juniorjavajoboffers.domain.register;

import org.junit.jupiter.api.Test;
import pl.zakrzewski.juniorjavajoboffers.domain.confirmation.ConfirmationFacade;
import pl.zakrzewski.juniorjavajoboffers.domain.confirmation.InMemoryConfirmationTokenRepository;
import pl.zakrzewski.juniorjavajoboffers.domain.emailsender.EmailSenderFacade;
import pl.zakrzewski.juniorjavajoboffers.domain.register.dto.RegisterRequestDto;
import pl.zakrzewski.juniorjavajoboffers.domain.register.dto.RegisterResultDto;
import pl.zakrzewski.juniorjavajoboffers.domain.register.dto.UserDto;
import pl.zakrzewski.juniorjavajoboffers.domain.register.exceptions.InvalidEmailAddressException;
import pl.zakrzewski.juniorjavajoboffers.domain.register.exceptions.UserAlreadyExistException;
import pl.zakrzewski.juniorjavajoboffers.domain.register.exceptions.UserNotFoundException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class RegisterFacadeTest {
    EmailSenderFacade emailSenderFacade = mock(EmailSenderFacade.class);
    RegisterFacade registerFacade = new RegisterFacade(
            new InMemoryRegisterRepository(),
            emailSenderFacade,
            new ConfirmationFacade(new InMemoryConfirmationTokenRepository()));

    @Test
    void should_register_user_and_user_not_enabled() {
        RegisterRequestDto registerRequestDto = new RegisterRequestDto("tomek", "tomekatomek@gmail.com");
        RegisterResultDto result = registerFacade.registerUser(registerRequestDto);

        assertAll(
                () -> assertThat(result.created()).isTrue(),
                () -> assertThat(result.enabled()).isFalse(),
                () -> assertThat(result.email()).isEqualTo("tomekatomek@gmail.com")
        );
    }

    @Test
    void should_find_user_by_email() {
        RegisterRequestDto registerRequestDto = new RegisterRequestDto("tomek", "tomekatomek@gmail.com");
        RegisterResultDto registerResultDto = registerFacade.registerUser(registerRequestDto);

        UserDto userDto = registerFacade.findByEmail("tomekatomek@gmail.com");

        assertThat(userDto.mail().equals("tomekatomek@gmail.com"));
    }

    @Test
    void should_throw_exception_when_user_is_already_subscribed() {
        RegisterRequestDto registerRequestDto = new RegisterRequestDto("tomek", "tomekatomek@gmail.com");
        RegisterRequestDto registerRequestDto2 = new RegisterRequestDto("andrzej", "tomekatomek@gmail.com");

        RegisterResultDto registerResultDto = registerFacade.registerUser(registerRequestDto);

        assertThrows(UserAlreadyExistException.class, () -> {
            RegisterResultDto registerResultDto2 = registerFacade.registerUser(registerRequestDto2);
        });
    }

    @Test
    void should_throw_exception_when_user_not_found() {
        assertThrows(UserNotFoundException.class, () -> {
            registerFacade.findByEmail("adfasdf@gmail.com");
        });
    }

    @Test
    void should_throw_exception_when_email_is_invalid() {
        RegisterRequestDto registerRequestDto = new RegisterRequestDto("tomek", "asd");
        assertThrows(InvalidEmailAddressException.class, () -> {
            RegisterResultDto registerResultDto = registerFacade.registerUser(registerRequestDto);
        });
    }

    @Test
    void should_delete_user_when_unsubscribing_from_newsletter() {
        RegisterRequestDto registerRequestDto = new RegisterRequestDto("Tomek", "tomekatomek@gmail.com");
        RegisterResultDto registerResultDto = registerFacade.registerUser(registerRequestDto);
        UserDto userDto = registerFacade.findByEmail("tomekatomek@gmail.com");
        String id = userDto.id();
        registerFacade.deleteUserAndConfirmationToken(id);
        assertThrows(UserNotFoundException.class, () -> registerFacade.findByEmail("tomekatomek@gmail.com"));
    }

    @Test
    void should_send_confirmation_email_after_user_register() {
        RegisterRequestDto registerRequestDto = new RegisterRequestDto("Tomek", "tomekatomek@gmail.com");
        RegisterResultDto result = registerFacade.registerUser(registerRequestDto);
        verify(emailSenderFacade).sendConfirmationEmail(result.email(), result.token());
    }
}



















