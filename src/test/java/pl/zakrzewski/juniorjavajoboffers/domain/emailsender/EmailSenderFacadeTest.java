package pl.zakrzewski.juniorjavajoboffers.domain.emailsender;

import org.junit.jupiter.api.Test;
import org.springframework.mail.javamail.JavaMailSender;
import org.thymeleaf.TemplateEngine;
import pl.zakrzewski.juniorjavajoboffers.domain.emailsender.exceptions.ConfirmedUsersNotFound;
import pl.zakrzewski.juniorjavajoboffers.domain.emailsender.exceptions.OffersNotFound;
import pl.zakrzewski.juniorjavajoboffers.domain.offer.dto.OfferDto;
import pl.zakrzewski.juniorjavajoboffers.domain.register.dto.EmailAndIdDto;

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;

public class EmailSenderFacadeTest {
    JavaMailSender javaMailSender = mock(JavaMailSender.class);
    TemplateEngine templateEngine = new TemplateEngine();
    EmailSenderService emailSenderService = new EmailSenderService(javaMailSender, templateEngine);
    EmailSenderFacade emailSenderFacade = new EmailSenderFacade(emailSenderService);

    @Test
    void should_throw_exception_when_no_new_offers_found() {
        List<OfferDto> offers = Arrays.asList(
        );
        List<EmailAndIdDto> users = Arrays.asList(
                new EmailAndIdDto("tomek@gmail.com", UUID.randomUUID().toString())
        );

        assertThrows(OffersNotFound.class, () -> emailSenderFacade.sendJobOffersEmail(offers, users));
    }

    @Test
    void sould_throw_exception_when_no_users_with_confirmed_account_found() {
        List<OfferDto> offers = Arrays.asList(
                new OfferDto("ABCD", "abcd", "7000-8000", "Warsaw","asd.com", false)
        );
        List<EmailAndIdDto> users = Arrays.asList(
        );

        assertThrows(ConfirmedUsersNotFound.class, () -> emailSenderFacade.sendJobOffersEmail(offers, users));
    }

}
