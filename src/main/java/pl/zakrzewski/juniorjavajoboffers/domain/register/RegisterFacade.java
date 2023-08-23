package pl.zakrzewski.juniorjavajoboffers.domain.register;

import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import pl.zakrzewski.juniorjavajoboffers.domain.emailsender.EmailSenderFacade;
import pl.zakrzewski.juniorjavajoboffers.domain.register.dto.ConfirmationTokenDto;
import pl.zakrzewski.juniorjavajoboffers.domain.register.dto.RegisterUserDto;
import pl.zakrzewski.juniorjavajoboffers.domain.register.dto.RegistrationResultDto;
import pl.zakrzewski.juniorjavajoboffers.domain.register.dto.UserDto;
import pl.zakrzewski.juniorjavajoboffers.domain.register.exceptions.InvalidEmailAddressException;
import pl.zakrzewski.juniorjavajoboffers.domain.register.exceptions.TokenNotFoundException;
import pl.zakrzewski.juniorjavajoboffers.domain.register.exceptions.UserAlreadyExistException;
import pl.zakrzewski.juniorjavajoboffers.domain.register.exceptions.UserNotFoundException;
import pl.zakrzewski.juniorjavajoboffers.domain.register.token.ConfirmationToken;
import pl.zakrzewski.juniorjavajoboffers.domain.register.token.ConfirmationTokenMapper;
import pl.zakrzewski.juniorjavajoboffers.domain.register.token.ConfirmationTokenRepository;
import pl.zakrzewski.juniorjavajoboffers.domain.register.token.ConfirmationTokenService;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.Optional;
import java.util.UUID;

@RequiredArgsConstructor
public class RegisterFacade {

    public static final String USER_WITH_THIS_EMAIL_HAS_ALREADY_SUBSCRIBED = "User with this email has already subscribed";
    private final RegisterRepository repository;
//    private final EmailSenderFacade emailSender;
//    private final ConfirmationTokenService confirmationTokenService;
    private final ConfirmationTokenRepository confirmationTokenRepository;
    public RegistrationResultDto registerUser(RegisterUserDto registerUserDto) {
        if (EmailValidator.validateEmail(registerUserDto.mail())) {
            Optional<User> user = repository.findByEmail(registerUserDto.mail());
            if (user.isEmpty()) {
                final User userToSave = UserMapper.mapRegisterUserDtoToUser(registerUserDto);
                User userSaved = repository.save(userToSave);
                ConfirmationToken confirmationToken = ConfirmationToken.builder()
                        .token(UUID.randomUUID().toString())
                        .createdAt(LocalDateTime.now())
                        .expiresAt(LocalDateTime.now().plusMinutes(30))
                        //TODO zaimplementowac jakos date confirmed at, zeby nie byla nullem od poczatku
                        .user(userSaved)
                        .build();


                ConfirmationToken tokenSaved = confirmationTokenRepository.save(confirmationToken);
//                emailSender.sendConfirmationEmail(userSaved.getEmail(), tokenSaved.getToken());
                return new RegistrationResultDto(userSaved.getId(), true, userSaved.getEmail(),
                        userSaved.isEnabled(), tokenSaved.getToken());
            } else {
                throw new UserAlreadyExistException(USER_WITH_THIS_EMAIL_HAS_ALREADY_SUBSCRIBED);
            }
        }
        throw new InvalidEmailAddressException(registerUserDto.mail());
    }

    public UserDto findByEmail(String email) {
        return repository.findByEmail(email)
                .map(UserMapper::mapUserToUserDto)
                .orElseThrow(() -> new UserNotFoundException(email));
    }

    public ConfirmationTokenDto findByToken(String token) {
        return confirmationTokenRepository.findByToken(token)
                .map(ConfirmationTokenMapper::mapConfirmationTokenToConfirmationTokenDto)
                .orElseThrow(() -> new TokenNotFoundException(token));
    }
}
