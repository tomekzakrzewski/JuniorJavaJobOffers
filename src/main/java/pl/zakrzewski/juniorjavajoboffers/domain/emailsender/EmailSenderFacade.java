package pl.zakrzewski.juniorjavajoboffers.domain.emailsender;

import lombok.RequiredArgsConstructor;
import org.springframework.mail.javamail.JavaMailSender;
import pl.zakrzewski.juniorjavajoboffers.domain.register.dto.UserDto;
import pl.zakrzewski.juniorjavajoboffers.domain.register.token.ConfirmationToken;

@RequiredArgsConstructor
public class EmailSenderFacade {

    private final JavaMailSender mailSender;

    public void sendConfirmationEmail(UserDto userDto, ConfirmationToken token) {
        
    }
}
