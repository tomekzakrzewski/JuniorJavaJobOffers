package pl.zakrzewski.juniorjavajoboffers.domain.register;

import lombok.AllArgsConstructor;
import pl.zakrzewski.juniorjavajoboffers.domain.register.dto.RegisterUserDto;
import pl.zakrzewski.juniorjavajoboffers.domain.register.dto.RegistrationResultDto;
import pl.zakrzewski.juniorjavajoboffers.domain.register.dto.UserDto;
import pl.zakrzewski.juniorjavajoboffers.domain.register.exceptions.InvalidEmailAddressException;
import pl.zakrzewski.juniorjavajoboffers.domain.register.exceptions.UserAlreadyExistException;
import pl.zakrzewski.juniorjavajoboffers.domain.register.exceptions.UserNotFoundException;

import java.util.Optional;

@AllArgsConstructor
public class RegisterFacade {

    public static final String USER_WITH_THIS_EMAIL_HAS_ALREADY_SUBSCRIBED = "User with this email has already subscribed";
    private final RegisterRepository repository;

    public RegistrationResultDto registerUser(RegisterUserDto registerUserDto) {
        if (ValidateEmailAddress.validateEmail(registerUserDto.mail())) {
            Optional<User> user = repository.findByEmail(registerUserDto.mail());
            if (user.isEmpty()) {
                final User userToSave = UserMapper.mapRegisterUserDtoToUser(registerUserDto);
                User userSaved = repository.save(userToSave);
                //create token
                //TODO send confirmation email
                return new RegistrationResultDto(userSaved.getId(), true, userSaved.getEmail(), userSaved.isEnabled());
            } else {
                throw new UserAlreadyExistException(USER_WITH_THIS_EMAIL_HAS_ALREADY_SUBSCRIBED);
            }
        }
        throw new InvalidEmailAddressException(registerUserDto.mail());
    }

    public UserDto findByEmail(String email) {
        return repository.findByEmail(email)
                .map(UserMapper::mapUserDtoToUser)
                .orElseThrow(() -> new UserNotFoundException(email));
    }
}
