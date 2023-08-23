package pl.zakrzewski.juniorjavajoboffers.register;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import pl.zakrzewski.juniorjavajoboffers.domain.register.RegisterFacade;
import pl.zakrzewski.juniorjavajoboffers.domain.register.dto.ConfirmationTokenDto;
import pl.zakrzewski.juniorjavajoboffers.domain.register.dto.RegisterUserDto;
import pl.zakrzewski.juniorjavajoboffers.domain.register.dto.RegistrationResultDto;
import pl.zakrzewski.juniorjavajoboffers.domain.register.dto.UserDto;
import pl.zakrzewski.juniorjavajoboffers.domain.register.exceptions.InvalidEmailAddressException;
import pl.zakrzewski.juniorjavajoboffers.domain.register.exceptions.TokenNotFoundException;
import pl.zakrzewski.juniorjavajoboffers.domain.register.exceptions.UserAlreadyExistException;
import pl.zakrzewski.juniorjavajoboffers.domain.register.exceptions.UserNotFoundException;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class RegisterFacadeTest {
    RegisterFacade registerFacade = new RegisterFacade(new InMemoryRegisterRepository(), new InMemoryConfirmationTokenRepository());

    @Test
    void should_register_user_and_user_not_enabled() {
        RegisterUserDto registerUserDto = new RegisterUserDto("tomek", "tomekatomek@gmail.com");
        RegistrationResultDto result = registerFacade.registerUser(registerUserDto);

        assertAll(
                () -> assertThat(result.created()).isTrue(),
                () -> assertThat(result.enabled()).isFalse(),
                () -> assertThat(result.email()).isEqualTo("tomekatomek@gmail.com")
        );
    }

    @Test
    void should_find_user_by_email() {
        RegisterUserDto registerUserDto = new RegisterUserDto("tomek", "tomekatomek@gmail.com");
        RegistrationResultDto registrationResultDto = registerFacade.registerUser(registerUserDto);

        UserDto userDto = registerFacade.findByEmail("tomekatomek@gmail.com");

        assertThat(userDto.mail().equals("tomekatomek@gmail.com"));
    }

    @Test
    void should_throw_exception_when_user_is_already_subscribed() {
        RegisterUserDto registerUserDto = new RegisterUserDto("tomek", "tomekatomek@gmail.com");
        RegisterUserDto registerUserDto2 = new RegisterUserDto("andrzej", "tomekatomek@gmail.com");

        RegistrationResultDto registrationResultDto = registerFacade.registerUser(registerUserDto);

        assertThrows(UserAlreadyExistException.class, () -> {
            RegistrationResultDto registrationResultDto2 = registerFacade.registerUser(registerUserDto2);
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
        RegisterUserDto registerUserDto = new RegisterUserDto("tomek", "asd");
        assertThrows(InvalidEmailAddressException.class, () -> {
            RegistrationResultDto registrationResultDto = registerFacade.registerUser(registerUserDto);
        });
    }

    @Test
    @Disabled
    void should_token_not_have_activated_date_when_created() {
        RegisterUserDto registerUserDto = new RegisterUserDto(("Tomekk"), "tomekatomek@gmail.com");
        RegistrationResultDto registrationResultDto = registerFacade.registerUser(registerUserDto);

        ConfirmationTokenDto confirmationTokenDto = registerFacade.findByToken(registrationResultDto.token());
        //TODO zaimplementowac jakos date confirmed at, zeby nie byla nullem od poczatku
    }

    @Test
    void should_find_confirmation_token_by_token() {
        RegisterUserDto registerUserDto = new RegisterUserDto("Tomek", "tomekatomek@gmail.com");
        RegistrationResultDto registrationResultDto = registerFacade.registerUser(registerUserDto);
        ConfirmationTokenDto confirmationTokenDto = registerFacade.findByToken(registrationResultDto.token());
        assertThat(confirmationTokenDto.getToken().equals(registrationResultDto.token()));
    }

    @Test
    void should_throw_exception_when_token_not_found() {
        assertThrows(TokenNotFoundException.class, () -> {
            registerFacade.findByToken(UUID.randomUUID().toString());
        });
    }
}



















