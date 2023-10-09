package pl.zakrzewski.juniorjavajoboffers.domain.offer;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.UuidGenerator;

import java.time.LocalDateTime;
import java.util.Objects;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Entity
@Table(name = "offers")
public class Offer {
    @Id
    @UuidGenerator
    private String id;
    private String company;
    private String position;
    private String city;
    private String salary;
    private String url;
    private boolean remote;
    private Long posted;

    // if company name, position name and time posted is the same, offer is the same
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Offer offer)) return false;
        return Objects.equals(company, offer.company) && Objects.equals(position, offer.position) && Objects.equals(posted, offer.posted);
    }

    @Override
    public int hashCode() {
        return Objects.hash(company, position, posted);
    }
}
