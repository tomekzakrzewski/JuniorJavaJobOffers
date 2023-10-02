package pl.zakrzewski.juniorjavajoboffers.domain.register;

import pl.zakrzewski.juniorjavajoboffers.domain.register.dto.EmailAndIdDto;
import pl.zakrzewski.juniorjavajoboffers.domain.register.dto.RegisterRequestDto;
import pl.zakrzewski.juniorjavajoboffers.domain.register.dto.UserDto;

class UserMapper {

    public static User mapRegisterUserDtoToUser(RegisterRequestDto registerRequestDto) {
        return User.builder()
                .email(registerRequestDto.mail())
                .username(registerRequestDto.username())
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

    public static UserDto mapUserToUserDto(User user) {
        return UserDto.builder()
                .id(user.getId())
                .username(user.getUsername())
                .mail(user.getEmail())
                .enabled(user.isEnabled())
                .build();
    }

    public static EmailAndIdDto mapUserToEmailAndIdDto(User user) {
        return EmailAndIdDto.builder()
                .id(user.getId())
                .email(user.getEmail())
                .build();
    }
}
