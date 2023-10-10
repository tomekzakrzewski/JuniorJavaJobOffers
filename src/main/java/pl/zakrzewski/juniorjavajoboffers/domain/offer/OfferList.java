package pl.zakrzewski.juniorjavajoboffers.domain.offer;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import pl.zakrzewski.juniorjavajoboffers.domain.offer.dto.OfferResponse;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OfferList {
    @JsonProperty("postings")
    private List<OfferResponse> offers;
}
