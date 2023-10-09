package pl.zakrzewski.juniorjavajoboffers.domain.offer;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import pl.zakrzewski.juniorjavajoboffers.domain.emailsender.EmailSenderFacade;
import pl.zakrzewski.juniorjavajoboffers.domain.offer.dto.OfferDto;
import pl.zakrzewski.juniorjavajoboffers.domain.register.RegisterFacade;

import java.util.List;

@Component
@AllArgsConstructor
public class OfferFacade {
    private final OfferService offerService;
    private final RegisterFacade registerFacade;
    private final EmailSenderFacade emailSenderFacade;

    public List<OfferDto> fetchAllOffersSaveAllIfNotExists() {
        return offerService.fetchAllOffersAndSaveAllIfNotExists()
                .stream()
                .map(OfferMapper::mapOfferToOfferDto)
                .toList();
    }

    public void sendEmailWithJobOffers() {
        emailSenderFacade.sendJobOffersEmail(fetchAllOffersSaveAllIfNotExists(),
                registerFacade.findEmailsAndIdsOfConfirmedUsers());
    }
}
