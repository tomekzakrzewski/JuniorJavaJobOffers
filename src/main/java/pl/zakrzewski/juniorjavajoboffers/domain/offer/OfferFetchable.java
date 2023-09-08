package pl.zakrzewski.juniorjavajoboffers.domain.offer;

import pl.zakrzewski.juniorjavajoboffers.domain.offer.dto.OfferResponse;

import java.util.List;

public interface OfferFetchable {

    List<OfferResponse> fetchOffersFromNofluffjobs();
}
