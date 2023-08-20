package pl.zakrzewski.juniorjavajoboffers.domain.register;

import pl.zakrzewski.juniorjavajoboffers.domain.register.dto.RegisterUserDto;
import pl.zakrzewski.juniorjavajoboffers.domain.register.dto.RegistrationResultDto;

public class UserMapper {

    public static User mapRegisterUserDtoToUser(RegisterUserDto registerUserDto) {
        return User.builder()
                .email(registerUserDto.mail())
                .username(registerUserDto.username())
                .build();
    }
}
