package pl.zakrzewski.juniorjavajoboffers.domain.offer;

import pl.zakrzewski.juniorjavajoboffers.domain.offer.dto.OfferDto;
import pl.zakrzewski.juniorjavajoboffers.domain.offer.dto.OfferResponse;

public class OfferMapper {

    public static OfferDto mapOfferToOfferDto(Offer offer) {
        return OfferDto.builder()
                .company(offer.getCompany())
                .position(offer.getPosition())
                .city(offer.getCity())
                .salary(offer.getSalary())
                .url(offer.getUrl())
                .remote(offer.isRemote())
                .build();
    }

    public static Offer mapOfferDtoToOffer(OfferDto offerDto) {
        return Offer.builder()
                .company(offerDto.getCompany())
                .position(offerDto.getPosition())
                .city(offerDto.getCity())
                .salary(offerDto.getSalary())
                .url(offerDto.getUrl())
                .remote(offerDto.isRemote())
                .build();
    }

    public static Offer mapFromOfferResponseToOffer(OfferResponse offerResponse) {
        return Offer.builder()
                .company(offerResponse.getName())
                .position(offerResponse.getTitle())
                .salary(offerResponse.getSalary().getFrom().toString() + " - " + offerResponse.getSalary().getTo())
                .url(offerResponse.getUrl())
                .remote(offerResponse.getLocation().isFullyRemote())
                .posted(offerResponse.getPosted())
                .build();
    }

    public static OfferDto mapFromOfferResponseToOfferDto(OfferResponse offerResponse) {
        return OfferDto.builder()
                .company(offerResponse.getName())
                .position(offerResponse.getTitle())
                .salary(offerResponse.getSalary().getFrom().toString() + " - " + offerResponse.getSalary().getTo())
                .url(offerResponse.getUrl())
                .remote(offerResponse.getLocation().isFullyRemote())
                .build();
    }
}
