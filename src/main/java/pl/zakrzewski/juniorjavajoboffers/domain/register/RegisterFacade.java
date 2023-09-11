package pl.zakrzewski.juniorjavajoboffers.domain.register;

import lombok.RequiredArgsConstructor;
import pl.zakrzewski.juniorjavajoboffers.domain.register.dto.*;
import pl.zakrzewski.juniorjavajoboffers.domain.register.exceptions.InvalidEmailAddressException;
import pl.zakrzewski.juniorjavajoboffers.domain.register.exceptions.TokenNotFoundException;
import pl.zakrzewski.juniorjavajoboffers.domain.register.exceptions.UserAlreadyExistException;
import pl.zakrzewski.juniorjavajoboffers.domain.register.exceptions.UserNotFoundException;
import pl.zakrzewski.juniorjavajoboffers.domain.register.token.ConfirmationToken;
import pl.zakrzewski.juniorjavajoboffers.domain.register.token.ConfirmationTokenMapper;
import pl.zakrzewski.juniorjavajoboffers.domain.register.token.ConfirmationTokenService;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
public class RegisterFacade {

    public static final String USER_WITH_THIS_EMAIL_HAS_ALREADY_SUBSCRIBED = "User with this email has already subscribed";
    private final RegisterRepository repository;
    //private final EmailSenderFacade emailSender;
    private final ConfirmationTokenService confirmationTokenService;
    public RegistrationResultDto registerUser(RegisterRequestDto registerRequestDto) {
        if (EmailValidator.validateEmail(registerRequestDto.mail())) {
            Optional<User> user = repository.findByEmail(registerRequestDto.mail());
            if (user.isEmpty()) {
                final User userToSave = UserMapper.mapRegisterUserDtoToUser(registerRequestDto);
                User userSaved = repository.save(userToSave);
                ConfirmationToken confirmationToken = confirmationTokenService.generateConfirmationToken(userSaved);
                ConfirmationToken tokenSaved = confirmationTokenService.saveConfirmationToken(confirmationToken);
                //emailSender.sendConfirmationEmail(userSaved.getEmail(), tokenSaved.getToken());
                return new RegistrationResultDto(userSaved.getId(), true, userSaved.getEmail(),
                        userSaved.isEnabled(), tokenSaved.getToken());
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

    public ConfirmationTokenDto findByToken(String token) {
        return confirmationTokenService.getToken(token)
                .map(ConfirmationTokenMapper::mapConfirmationTokenToConfirmationTokenDto)
                .orElseThrow(() -> new TokenNotFoundException(token));
    }

    public ConfirmationTokenResultDto confirmToken(String token) {
        ConfirmationTokenResultDto confirmationTokenResultDto =  confirmationTokenService.confirmToken(token);
        repository.enableUser(confirmationTokenService.getToken(token).get().getUser().getEmail());
        return confirmationTokenResultDto;
    }

    public List<String> findEmailsOfConfirmedUsers() {
        return repository.getUserByEnabledTrue()
                .stream()
                .map(user -> user.getEmail())
                .toList();
    }
}
