package pl.zakrzewski.juniorjavajoboffers.domain.offer;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@AllArgsConstructor
@Service
public class OfferService {
    //todo
    //fetch offer and save not existing ones
    //fetch offers
    //filter not existing ones
    //getAllNewOffers - by date
    private final OfferFetchable client;
    private final OfferRepository offerRepository;

    public List<Offer> fetchAllOffersAndSaveAllIfNotExists() {
        List<Offer> jobOffers = fetchOffers();
        final List<Offer> offers = filterNotExistingOffers(jobOffers);
        return offerRepository.saveAll(offers);

    }


    private List<Offer> fetchOffers() {
        return client.fetchOffersFromNofluffjobs()
                .stream()
                .map(OfferMapper::mapFromOfferResponseToOffer)
                .toList();
    }


    private List<Offer> filterNotExistingOffers(List<Offer> jobOffers) {
        return jobOffers.stream()
                .distinct()
                .filter(offer -> !offerRepository.existsOfferByCompanyAndPosition(offer.getCompany(), offer.getPosition()))
                .toList();
    }

}
