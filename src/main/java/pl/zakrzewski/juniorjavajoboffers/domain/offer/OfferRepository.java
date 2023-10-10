package pl.zakrzewski.juniorjavajoboffers.domain.offer;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface OfferRepository extends JpaRepository<Offer, String> {

    boolean existsOfferByCompanyAndPosted(String companyName, Long posted);
    Optional<List<Offer>> getOffersByCreatedDate(LocalDate date);
}
