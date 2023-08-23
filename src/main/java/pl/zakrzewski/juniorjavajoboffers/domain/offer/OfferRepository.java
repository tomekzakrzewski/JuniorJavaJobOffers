package pl.zakrzewski.juniorjavajoboffers.domain.offer;

import org.springframework.data.jpa.repository.JpaRepository;

public interface OfferRepository extends JpaRepository<Offer, String> {
    boolean existsByOfferUrl(String offerUrl);
}
