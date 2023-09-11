package pl.zakrzewski.juniorjavajoboffers.domain.offer;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import pl.zakrzewski.juniorjavajoboffers.domain.offer.dto.OfferDto;

import java.util.List;
import java.util.stream.Collectors;

@Component
@AllArgsConstructor
public class OfferFacade {

    private final OfferService offerService;

    /*
    todo
    find all offers
    fetch and save all not existing offers
    find offers from today
     */

    public List<OfferDto> fetchAllOffersSaveAllIfNotExists() {
        return offerService.fetchAllOffersAndSaveAllIfNotExists()
                .stream()
                .map(OfferMapper::mapOfferToOfferDto)
                .toList();
    }


}
