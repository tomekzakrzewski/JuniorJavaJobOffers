package pl.zakrzewski.juniorjavajoboffers.register;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import pl.zakrzewski.juniorjavajoboffers.domain.register.RegisterFacade;
import pl.zakrzewski.juniorjavajoboffers.domain.register.dto.*;
import pl.zakrzewski.juniorjavajoboffers.domain.register.exceptions.InvalidEmailAddressException;
import pl.zakrzewski.juniorjavajoboffers.domain.register.exceptions.TokenNotFoundException;
import pl.zakrzewski.juniorjavajoboffers.domain.register.exceptions.UserAlreadyExistException;
import pl.zakrzewski.juniorjavajoboffers.domain.register.exceptions.UserNotFoundException;
import pl.zakrzewski.juniorjavajoboffers.domain.register.token.ConfirmationTokenService;

import java.time.LocalDateTime;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class RegisterFacadeTest {
    RegisterFacade registerFacade = new RegisterFacade(new InMemoryRegisterRepository(), new ConfirmationTokenService(new InMemoryConfirmationTokenRepository()));

    @Test
    void should_register_user_and_user_not_enabled() {
        RegisterRequestDto registerRequestDto = new RegisterRequestDto("tomek", "tomekatomek@gmail.com");
        RegistrationResultDto result = registerFacade.registerUser(registerRequestDto);

        assertAll(
                () -> assertThat(result.created()).isTrue(),
                () -> assertThat(result.enabled()).isFalse(),
                () -> assertThat(result.email()).isEqualTo("tomekatomek@gmail.com")
        );
    }

    @Test
    void should_find_user_by_email() {
        RegisterRequestDto registerRequestDto = new RegisterRequestDto("tomek", "tomekatomek@gmail.com");
        RegistrationResultDto registrationResultDto = registerFacade.registerUser(registerRequestDto);

        UserDto userDto = registerFacade.findByEmail("tomekatomek@gmail.com");

        assertThat(userDto.mail().equals("tomekatomek@gmail.com"));
    }

    @Test
    void should_throw_exception_when_user_is_already_subscribed() {
        RegisterRequestDto registerRequestDto = new RegisterRequestDto("tomek", "tomekatomek@gmail.com");
        RegisterRequestDto registerRequestDto2 = new RegisterRequestDto("andrzej", "tomekatomek@gmail.com");

        RegistrationResultDto registrationResultDto = registerFacade.registerUser(registerRequestDto);

        assertThrows(UserAlreadyExistException.class, () -> {
            RegistrationResultDto registrationResultDto2 = registerFacade.registerUser(registerRequestDto2);
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
            RegistrationResultDto registrationResultDto = registerFacade.registerUser(registerRequestDto);
        });
    }

    @Test
    @Disabled
    void should_token_not_have_activated_date_when_created() {
        RegisterRequestDto registerRequestDto = new RegisterRequestDto(("Tomekk"), "tomekatomek@gmail.com");
        RegistrationResultDto registrationResultDto = registerFacade.registerUser(registerRequestDto);

        ConfirmationTokenDto confirmationTokenDto = registerFacade.findByToken(registrationResultDto.token());
    }

    @Test
    void should_find_confirmation_token_by_token() {
        RegisterRequestDto registerRequestDto = new RegisterRequestDto("Tomek", "tomekatomek@gmail.com");
        RegistrationResultDto registrationResultDto = registerFacade.registerUser(registerRequestDto);
        ConfirmationTokenDto confirmationTokenDto = registerFacade.findByToken(registrationResultDto.token());
        assertThat(confirmationTokenDto.getToken().equals(registrationResultDto.token()));
    }

    @Test
    void should_throw_exception_when_token_not_found() {
        assertThrows(TokenNotFoundException.class, () -> {
            registerFacade.findByToken(UUID.randomUUID().toString());
        });
    }

    @Test
    void should_set_user_enabled_true_when_confirmed() {
        RegisterRequestDto registerRequestDto = new RegisterRequestDto("Tomek", "tomekatomek@gmail.com");
        RegistrationResultDto registrationResultDto = registerFacade.registerUser(registerRequestDto);
        ConfirmationTokenResultDto confirmationTokenResultDto = registerFacade.confirmToken(registrationResultDto.token());
        assertThat(registerFacade.findByEmail("tomekatomek@gmail.com").enabled()).isTrue();
    }

    @Test
    void should_set_confirmed_at_as_current_date_when_confirmed() {
        RegisterRequestDto registerRequestDto = new RegisterRequestDto("Tomek", "tomekatomek@gmail.com");
        RegistrationResultDto registrationResultDto = registerFacade.registerUser(registerRequestDto);
        ConfirmationTokenResultDto confirmationTokenResultDto =  registerFacade.confirmToken(registrationResultDto.token());
        assertThat(registerFacade.findByToken(registrationResultDto.token()).getConfirmedAt().equals(LocalDateTime.now()));

    }

    @Test
    void should_find_all_emails_of_users_that_confirmed_account() {
        RegisterRequestDto registerRequestDto = new RegisterRequestDto("Tomek", "tomekatomek@gmail.com");
        RegistrationResultDto registrationResultDto = registerFacade.registerUser(registerRequestDto);
        ConfirmationTokenResultDto confirmationTokenResultDto =  registerFacade.confirmToken(registrationResultDto.token());

        RegisterRequestDto registerRequestDtoSecond = new RegisterRequestDto("Damian", "domek@gmail.com");
        RegistrationResultDto registrationResultDtoSecond = registerFacade.registerUser(registerRequestDtoSecond);

        assertThat(registerFacade.findEmailsOfConfirmedUsers().size()).isEqualTo(1);
        assertThat(registerFacade.findEmailsOfConfirmedUsers().stream().findFirst().equals("tomekatomek@gmail.com"));
    }
}



















