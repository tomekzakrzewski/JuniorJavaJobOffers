package pl.zakrzewski.juniorjavajoboffers.domain.offer;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Offer {
    private String company;
    private String position;
    private String city;
    private String salary;
    private String url;
    private boolean remote;
}
