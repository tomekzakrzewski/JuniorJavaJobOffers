package pl.zakrzewski.juniorjavajoboffers.domain.emailsender;

import lombok.RequiredArgsConstructor;
import org.springframework.mail.javamail.JavaMailSender;
import pl.zakrzewski.juniorjavajoboffers.domain.emailsender.exceptions.ConfirmedUsersNotFound;
import pl.zakrzewski.juniorjavajoboffers.domain.emailsender.exceptions.OffersNotFound;
import pl.zakrzewski.juniorjavajoboffers.domain.offer.OfferFacade;
import pl.zakrzewski.juniorjavajoboffers.domain.offer.dto.OfferDto;
import pl.zakrzewski.juniorjavajoboffers.domain.register.RegisterFacade;
import pl.zakrzewski.juniorjavajoboffers.domain.register.dto.UserDto;
import pl.zakrzewski.juniorjavajoboffers.domain.register.token.ConfirmationToken;

import java.util.List;

@RequiredArgsConstructor
public class EmailSenderFacade {

    private final EmailSenderService emailSenderService;
    private final OfferFacade offerFacade;
    private final RegisterFacade registerFacade;
    public void sendConfirmationEmail(String email, String token) {
        emailSenderService.sendConfimationEmail(email, token);

    }


    public void sendJobOffersEmail() {
        List<String> emails = registerFacade.findEmailsOfConfirmedUsers();
        List<OfferDto> offers = offerFacade.fetchAllOffersSaveAllIfNotExists();
        if (emails.isEmpty()) {
            throw new ConfirmedUsersNotFound();
        }
        if (offers.isEmpty()) {
            throw new OffersNotFound();
        }
        emailSenderService.sendOffersEmail(emails, offers);
    }

}
