package pl.zakrzewski.juniorjavajoboffers.domain.register;

import jakarta.servlet.Registration;
import lombok.AllArgsConstructor;
import pl.zakrzewski.juniorjavajoboffers.domain.register.dto.RegisterUserDto;
import pl.zakrzewski.juniorjavajoboffers.domain.register.dto.RegistrationResultDto;
import pl.zakrzewski.juniorjavajoboffers.domain.register.exceptions.UserAlreadyExistException;

import java.util.Optional;

@AllArgsConstructor
public class RegisterFacade {

    public static final String USER_WITH_THIS_EMAIL_HAS_ALREADY_SUBSCRIBED = "User with this email has already subscribed";
    private final RegisterRepository repository;

    public RegistrationResultDto RegisterUser(RegisterUserDto registerUserDto) {
        Optional<User> user = repository.findByEmail(registerUserDto.mail());
        if (user.isEmpty()) {
            final User userToSave = UserMapper.mapRegisterUserDtoToUser(registerUserDto);
            User userSaved = repository.save(userToSave);
            //TODO send confirmation email
            return new RegistrationResultDto(userSaved.getId(), true, userSaved.getUsername(), userSaved.isEnabled());


        } else {
            throw new UserAlreadyExistException(USER_WITH_THIS_EMAIL_HAS_ALREADY_SUBSCRIBED);
        }

    }




}
