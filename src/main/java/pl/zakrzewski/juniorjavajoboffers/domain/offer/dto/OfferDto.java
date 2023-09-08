package pl.zakrzewski.juniorjavajoboffers.domain.offer.dto;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class OfferDto {
    private String company;
    private String position;
    private String city;
    private String salary;
    private String url;
    private boolean remote;

}
