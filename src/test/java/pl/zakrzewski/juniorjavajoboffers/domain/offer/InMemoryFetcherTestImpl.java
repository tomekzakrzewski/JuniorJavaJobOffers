package pl.zakrzewski.juniorjavajoboffers.domain.offer;

import pl.zakrzewski.juniorjavajoboffers.domain.offer.OfferFetchable;
import pl.zakrzewski.juniorjavajoboffers.domain.offer.OfferList;
import pl.zakrzewski.juniorjavajoboffers.domain.offer.dto.OfferResponse;

import java.util.List;

public class InMemoryFetcherTestImpl implements OfferFetchable {
    private OfferList offerList;

    public InMemoryFetcherTestImpl(OfferList offerList) {
        this.offerList = offerList;
    }

    @Override
    public List<OfferResponse> fetchOffersFromNofluffjobs() {
        return offerList.getOffers();
    }
}
