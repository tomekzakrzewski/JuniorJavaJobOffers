package pl.zakrzewski.juniorjavajoboffers.domain.offer;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OfferRepository extends JpaRepository<Offer, String> {

    boolean existsOfferByCompanyAndPosition(String companyName, String position);
    boolean existsOfferByCompanyAndPosted(String companyName, Long posted);
}
