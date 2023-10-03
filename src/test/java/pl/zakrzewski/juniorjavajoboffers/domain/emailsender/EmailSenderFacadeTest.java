package pl.zakrzewski.juniorjavajoboffers.domain.emailsender;

import org.junit.jupiter.api.Test;
import org.springframework.mail.javamail.JavaMailSender;
import org.thymeleaf.TemplateEngine;
import pl.zakrzewski.juniorjavajoboffers.domain.emailsender.exceptions.ConfirmedUsersNotFound;
import pl.zakrzewski.juniorjavajoboffers.domain.emailsender.exceptions.OffersNotFound;
import pl.zakrzewski.juniorjavajoboffers.domain.offer.dto.OfferDto;
import pl.zakrzewski.juniorjavajoboffers.domain.register.dto.EmailAndIdDto;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class EmailSenderFacadeTest {
    EmailSenderService emailSenderService = mock(EmailSenderService.class);
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
                new OfferDto("ABCD", "abcd", "7000-8000", "Warsaw", "asd.com", false)
        );
        List<EmailAndIdDto> users = Arrays.asList(
        );

        assertThrows(ConfirmedUsersNotFound.class, () -> emailSenderFacade.sendJobOffersEmail(offers, users));
    }

    @Test
    void should_send_email_when_new_offers_and_confirmed_users() {
        List<OfferDto> offers = Arrays.asList(
                new OfferDto("ABCD", "abcd", "6000-7000", "Warsaw", "abcd.com", true),
                new OfferDto("ABCDEF", "abcdef", "6000-7000", "Wroclaw", "abcdef.com", true),
                new OfferDto("GHIJ", "ghij", "8000-10000", "Warsaw", "ghij.com", false),
                new OfferDto("ABCD", "abcd", "6000-7000", "Warsaw", "abcd.com", true)
        );

        List<EmailAndIdDto> users = Arrays.asList(
                new EmailAndIdDto("tomek@gmail.com", UUID.randomUUID().toString()),
                new EmailAndIdDto("marek@gmail.com", UUID.randomUUID().toString()),
                new EmailAndIdDto("kuba@gmail.com", UUID.randomUUID().toString())
        );
        emailSenderFacade.sendJobOffersEmail(offers, users);
        verify(emailSenderService).sendOffersEmail(users, offers);
    }

    @Test
    void should_send_confirmation_email() {
        String email = "tomek@gmail.com";
        String token = UUID.randomUUID().toString();
        emailSenderFacade.sendConfirmationEmail(email, token);
        verify(emailSenderService).sendConfirmationEmail(email, token);
    }

}
