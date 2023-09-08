package pl.zakrzewski.juniorjavajoboffers.offer;

import pl.zakrzewski.juniorjavajoboffers.domain.offer.OfferFacade;
import pl.zakrzewski.juniorjavajoboffers.domain.offer.OfferList;
import pl.zakrzewski.juniorjavajoboffers.domain.offer.OfferService;
import pl.zakrzewski.juniorjavajoboffers.domain.offer.dto.OfferResponse;

import java.util.List;

public class OfferFacadeTestConfiguration {
    private final InMemoryOfferRepository offerRepository;
    private final InMemoryFetcherTestImpl inMemoryFetcherTest;

    OfferFacadeTestConfiguration() {
        List<OfferResponse> offerResponse = List.of(
                new OfferResponse("Abc", "Junior Developer", "1", new OfferResponse.Location(true),
                        new OfferResponse.Salary(8000L, 9000L)),
                new OfferResponse("Def", "Java Developer", "2", new OfferResponse.Location(false),
                        new OfferResponse.Salary(4000L, 6000L)),
                new OfferResponse("Ghi", "Junior Java", "3", new OfferResponse.Location(false),
                        new OfferResponse.Salary(7000L, 8000L)),
                new OfferResponse("Jkl", "Java Software Developer", "4", new OfferResponse.Location(true),
                        new OfferResponse.Salary(6000L, 10000L)),
                new OfferResponse("Mno", "Java", "5", new OfferResponse.Location(true),
                        new OfferResponse.Salary(10000L, 13000L)),
                new OfferResponse("Pqr", "Developer", "6", new OfferResponse.Location(false),
                        new OfferResponse.Salary(3000L, 6000L)),
                new OfferResponse("Stu", "Junior Java", "7", new OfferResponse.Location(true),
                        new OfferResponse.Salary(4000L, 8000L))
                );

        OfferList offerList = new OfferList(offerResponse);
        this.offerRepository = new InMemoryOfferRepository();
        this.inMemoryFetcherTest = new InMemoryFetcherTestImpl(offerList);
    }

    OfferFacadeTestConfiguration(OfferList remoteClientOffers) {
        this.inMemoryFetcherTest = new InMemoryFetcherTestImpl(remoteClientOffers);
        this.offerRepository = new InMemoryOfferRepository();
    }

    OfferFacade offerFacadeForTests() {
        return new OfferFacade(new OfferService(inMemoryFetcherTest, offerRepository));
    }


}
