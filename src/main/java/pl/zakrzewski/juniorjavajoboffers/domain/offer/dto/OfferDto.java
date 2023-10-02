package pl.zakrzewski.juniorjavajoboffers.domain.offer.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
@AllArgsConstructor
public class OfferDto {
    private String company;
    private String position;
    private String salary;
    private String city;
    private String url;
    private boolean remote;

    public static class OfferDtoBuilder {
        public OfferDtoBuilder url(String url) {
            this.url = "https://nofluffjobs.com/job/" + url;
            return this;
        }
    }

    @Override
    public String toString() {
        return company + " " + position + " " +  salary + " " + "https://nofluffjobs.com/job/" + url +
                " is remote: " + remote + "\n";
    }


}
