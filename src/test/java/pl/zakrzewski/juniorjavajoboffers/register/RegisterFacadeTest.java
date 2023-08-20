package pl.zakrzewski.juniorjavajoboffers.register;

import org.junit.jupiter.api.Test;
import pl.zakrzewski.juniorjavajoboffers.domain.register.RegisterFacade;
import pl.zakrzewski.juniorjavajoboffers.domain.register.dto.RegisterUserDto;
import pl.zakrzewski.juniorjavajoboffers.domain.register.dto.RegistrationResultDto;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

public class RegisterFacadeTest {
    RegisterFacade registerFacade = new RegisterFacade(new InMemoryRegisterRepository());

    @Test
    void should_register_user_and_user_not_enabled() {
        RegisterUserDto registerUserDto = new RegisterUserDto("tomek", "tomekatomek@gmail.com");
        RegistrationResultDto result = registerFacade.RegisterUser(registerUserDto);

        assertAll(
                () -> assertThat(result.created()).isTrue(),
                () -> assertThat(result.enabled()).isFalse(),
                () -> assertThat(result.username()).isEqualTo("tomek")
        );


    }
}
