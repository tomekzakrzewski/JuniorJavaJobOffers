package pl.zakrzewski.juniorjavajoboffers.domain.offer.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OfferResponse {
    private String name;
    private String title;
    private String url;
    private Location location;
    private Salary salary;
    private Long posted;


    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Location {
        private boolean fullyRemote;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Salary {
        private Long from;
        private Long to;
    }

}

