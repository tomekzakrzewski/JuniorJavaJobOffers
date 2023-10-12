package pl.zakrzewski.juniorjavajoboffers.domain.register;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import pl.zakrzewski.juniorjavajoboffers.domain.confirmation.ConfirmationFacade;
import pl.zakrzewski.juniorjavajoboffers.domain.confirmation.dto.ConfirmationTokenResultDto;
import pl.zakrzewski.juniorjavajoboffers.domain.emailsender.EmailSenderFacade;
import pl.zakrzewski.juniorjavajoboffers.domain.register.dto.*;
import pl.zakrzewski.juniorjavajoboffers.domain.register.exceptions.InvalidEmailAddressException;
import pl.zakrzewski.juniorjavajoboffers.domain.register.exceptions.UserAlreadyExistException;
import pl.zakrzewski.juniorjavajoboffers.domain.register.exceptions.UserNotFoundException;
import pl.zakrzewski.juniorjavajoboffers.domain.confirmation.ConfirmationToken;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Component
public class RegisterFacade {
    public static final String USER_WITH_THIS_EMAIL_HAS_ALREADY_SUBSCRIBED = "User with this email has already subscribed";
    private final RegisterRepository repository;
    private final EmailSenderFacade emailSenderFacade;
    private final ConfirmationFacade confirmationFacade;

    public RegisterResultDto registerUser(RegisterRequestDto registerRequestDto) {
        if (validateEmail(registerRequestDto.mail())) {
            if (!userExistsByEmail(registerRequestDto.mail())) {
                User user = saveUser(registerRequestDto);
                ConfirmationToken confirmationToken = confirmationFacade.generateConfirmationToken(user);
                ConfirmationToken tokenSaved = confirmationFacade.saveConfirmationToken(confirmationToken);
                sendConfirmationEmail(user.getEmail(), tokenSaved.getToken());
                return registrationResult(user, tokenSaved);
            } else {
                throw new UserAlreadyExistException(USER_WITH_THIS_EMAIL_HAS_ALREADY_SUBSCRIBED);
            }
        }
        throw new InvalidEmailAddressException(registerRequestDto.mail());
    }

    public UserDto findByEmail(String email) {
        return repository.findByEmail(email)
                .map(UserMapper::mapUserToUserDto)
                .orElseThrow(() -> new UserNotFoundException(email));
    }

    public void setUserEnabledByEmail(String email) {
        repository.enableUser(email);
    }

    public List<UserIdEmailDto> findEmailsAndIdsOfConfirmedUsers() {
        return repository.getUserByEnabledTrue()
                .stream()
                .map(UserMapper::mapUserToEmailAndIdDto)
                .toList();
    }

    public String deleteUserAndConfirmationToken(String id) {
        if (userExistsById(id)) {
            confirmationFacade.deleteConfirmationTokenByUserId(id);
            return repository.deleteUserById(id);
        } else {
            throw new UserNotFoundException(id);
        }
    }

    private boolean userExistsById(String id) {
        Optional<User> user =  repository.findById(id);
        return user.isPresent();
    }

    private boolean validateEmail(String email) {
        return EmailValidator.validateEmail(email);
    }

    private void sendConfirmationEmail(String email, String token) {
        emailSenderFacade.sendConfirmationEmail(email, token);
    }

    private boolean userExistsByEmail(String email) {
        Optional<User> user = repository.findByEmail(email);
        return user.isPresent();
    }

    private RegisterResultDto registrationResult(User user, ConfirmationToken confirmationToken) {
        return RegisterResultDto.builder()
                .id(user.getId())
                .created(true)
                .enabled(false)
                .email(user.getEmail())
                .token(confirmationToken.getToken())
                .build();
    }

    private User saveUser(RegisterRequestDto registerRequestDto) {
        User user = UserMapper.mapRegisterUserDtoToUser(registerRequestDto);
        return repository.save(user);
    }
}
