package pl.zakrzewski.juniorjavajoboffers.domain.offer;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Objects;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class Offer {
    private String id;
    private String company;
    private String position;
    private String city;
    private String salary;
    private String url;
    private boolean remote;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Offer offer)) return false;
        return Objects.equals(company, offer.company) && Objects.equals(position, offer.position) && Objects.equals(salary, offer.salary);
    }

    @Override
    public int hashCode() {
        return Objects.hash(company, position, salary);
    }
}
