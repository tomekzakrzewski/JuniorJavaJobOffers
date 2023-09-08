package pl.zakrzewski.juniorjavajoboffers.infrastructure.offer.scheduler;

import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;
import pl.zakrzewski.juniorjavajoboffers.domain.offer.OfferFacade;

@Component
@AllArgsConstructor
@Log4j2
public class HttpOffersScheduler {
    private final OfferFacade offerFacade;

}
