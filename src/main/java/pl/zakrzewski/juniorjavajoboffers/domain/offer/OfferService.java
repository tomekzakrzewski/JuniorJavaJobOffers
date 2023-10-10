package pl.zakrzewski.juniorjavajoboffers.domain.offer;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import pl.zakrzewski.juniorjavajoboffers.domain.offer.dto.OfferDto;
import pl.zakrzewski.juniorjavajoboffers.domain.offer.exceptions.NewOffersNotFound;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@Service
class OfferService {
    private final OfferFetchable client;
    private final OfferRepository offerRepository;

    public List<Offer> fetchAllOffersAndSaveAllIfNotExists() {
        List<Offer> jobOffers = fetchOffers();
        List<Offer> offers = filterNotExistingOffers(jobOffers);
        addCurrentDateAsCreatedDate(offers);
        return offerRepository.saveAll(offers);

    }

    public List<OfferDto> findAllNewAddedOffers() {
        Optional<List<Offer>> jobOffers = offerRepository.getOffersByCreatedDate(LocalDate.now());

        if (jobOffers.isEmpty()) {
            throw new NewOffersNotFound();
        }

        List<OfferDto> offers = jobOffers.get()
                .stream()
                .map(OfferMapper::mapOfferToOfferDto)
                .toList();
        return offers;
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
                .filter(offer -> !offerRepository.existsOfferByCompanyAndPosted(offer.getCompany(), offer.getPosted()))
                .toList();
    }

    private void addCurrentDateAsCreatedDate(List<Offer> jobOffers) {
        for (Offer o : jobOffers) {
            o.setCreatedDate(LocalDate.now());
        }
    }
}
