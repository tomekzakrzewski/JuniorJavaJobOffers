package pl.zakrzewski.juniorjavajoboffers.domain.offer.dto;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class OfferDto {
    private String company;
    private String position;
    private String salary;
    private String city;
    private String url;
    private boolean remote;

    @Override
    public String toString() {
        return company + " " + position + " " +  salary + " " + "https://nofluffjobs.com/job/" + url +
                " is remote: " + remote + "\n";
    }
}
