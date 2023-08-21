package pl.zakrzewski.juniorjavajoboffers.domain.register;

import pl.zakrzewski.juniorjavajoboffers.domain.register.dto.RegisterUserDto;
import pl.zakrzewski.juniorjavajoboffers.domain.register.dto.UserDto;

public class UserMapper {

    public static User mapRegisterUserDtoToUser(RegisterUserDto registerUserDto) {
        return User.builder()
                .email(registerUserDto.mail())
                .username(registerUserDto.username())
                .build();
    }

    public static User mapUserDtoToUser(UserDto userDto) {
        return User.builder()
                .id(userDto.id())
                .username(userDto.username())
                .email(userDto.mail())
                .enabled(userDto.enabled())
                .build();
    }

    public static UserDto mapUserDtoToUser(User user) {
        return UserDto.builder()
                .id(user.getId())
                .username(user.getUsername())
                .mail(user.getEmail())
                .enabled(user.isEnabled())
                .build();
    }
}
