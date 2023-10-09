package pl.zakrzewski.juniorjavajoboffers.domain.offer;

import pl.zakrzewski.juniorjavajoboffers.domain.emailsender.EmailSenderFacade;
import pl.zakrzewski.juniorjavajoboffers.domain.offer.dto.OfferResponse;
import pl.zakrzewski.juniorjavajoboffers.domain.register.RegisterFacade;

import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.mock;

public class OfferFacadeTestConfiguration {
    private final InMemoryOfferRepository offerRepository;
    private final InMemoryFetcherTestImpl inMemoryFetcherTest;
    EmailSenderFacade emailSenderFacade = mock(EmailSenderFacade.class);
    RegisterFacade registerFacade = mock(RegisterFacade.class);


    OfferFacadeTestConfiguration() {
        List<OfferResponse> offerResponse = List.of(
                new OfferResponse("Abc", "Junior Developer", "1", new OfferResponse.Location(true),
                        new OfferResponse.Salary(8000L, 9000L), 1696587756840L),
                new OfferResponse("Def", "Java Developer", "2", new OfferResponse.Location(false),
                        new OfferResponse.Salary(4000L, 6000L), 1696587756849L),
                new OfferResponse("Ghi", "Junior Java", "3", new OfferResponse.Location(false),
                        new OfferResponse.Salary(7000L, 8000L), 1696587756841L),
                new OfferResponse("Jkl", "Java Software Developer", "4", new OfferResponse.Location(true),
                        new OfferResponse.Salary(6000L, 10000L), 1696587756830L),
                new OfferResponse("Mno", "Java", "5", new OfferResponse.Location(true),
                        new OfferResponse.Salary(10000L, 13000L), 1696587756820L),
                new OfferResponse("Pqr", "Developer", "6", new OfferResponse.Location(false),
                        new OfferResponse.Salary(3000L, 6000L), 1696587756822L),
                new OfferResponse("Stu", "Junior Java", "7", new OfferResponse.Location(true),
                        new OfferResponse.Salary(4000L, 8000L), 1696587756835L)
        );

        OfferList offerList = new OfferList();
        offerList.setOffers(offerResponse);
        this.offerRepository = new InMemoryOfferRepository();
        this.inMemoryFetcherTest = new InMemoryFetcherTestImpl(offerList);
    }

    OfferFacadeTestConfiguration(OfferList remoteClientOffers) {
        this.inMemoryFetcherTest = new InMemoryFetcherTestImpl(remoteClientOffers);
        this.offerRepository = new InMemoryOfferRepository();
    }

    OfferFacade offerFacadeForTests() {
        return new OfferFacade(new OfferService(inMemoryFetcherTest, offerRepository), registerFacade, emailSenderFacade);
    }


}
