package pl.zakrzewski.juniorjavajoboffers.domain.register;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import pl.zakrzewski.juniorjavajoboffers.domain.emailsender.EmailSenderFacade;
import pl.zakrzewski.juniorjavajoboffers.domain.register.token.ConfirmationTokenService;

@Configuration
public class RegisterFacadeConfiguration {
    @Bean
    RegisterFacade registerFacade(RegisterRepository repository, EmailSenderFacade emailSenderFacade, ConfirmationTokenService confirmationTokenService) {
        return new RegisterFacade(repository, emailSenderFacade, confirmationTokenService);
    }
}
