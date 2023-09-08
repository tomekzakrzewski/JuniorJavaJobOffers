package pl.zakrzewski.juniorjavajoboffers.offer;

import org.junit.jupiter.api.Test;
import pl.zakrzewski.juniorjavajoboffers.domain.offer.OfferFacade;
import pl.zakrzewski.juniorjavajoboffers.domain.offer.OfferList;
import pl.zakrzewski.juniorjavajoboffers.domain.offer.dto.OfferDto;
import pl.zakrzewski.juniorjavajoboffers.domain.offer.dto.OfferResponse;

import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class OfferFacadeTest {

    @Test
    public void should_fetch_and_save_all_offers_when_repository_is_empty() {
        OfferFacade offerFacade = new OfferFacadeTestConfiguration().offerFacadeForTests();
        List<OfferDto> result = offerFacade.fetchAllOffersSaveAllIfNotExists();
        assertThat(result.size()).isEqualTo(7);
    }

    @Test
    public void should_save_only_unique_offers() {
        OfferList offerList = new OfferList(
                List.of(
                        new OfferResponse("ABCD", "abcd", "abcd",
                                new OfferResponse.Location(false), new OfferResponse.Salary(5000L, 6000L)
                        ),
                        new OfferResponse("EFGH", "efgx", "efgh",
                                new OfferResponse.Location(false), new OfferResponse.Salary(5000L, 6000L)
                        ),
                        new OfferResponse("IJKL", "ijkl", "ijkl",
                                new OfferResponse.Location(true), new OfferResponse.Salary(8000L, 9000L)
                        ),
                        new OfferResponse("Google", "Junior Java developer", "https://google.com",
                                new OfferResponse.Location(true), new OfferResponse.Salary(8000L, 9000L)
                        ),
                        new OfferResponse("Google", "Junior Java developer", "https://google.com",
                                new OfferResponse.Location(true), new OfferResponse.Salary(8000L, 9000L)
                        )
                )
        );

        OfferFacade offerFacade = new OfferFacadeTestConfiguration(offerList).offerFacadeForTests();
        List<OfferDto> response = offerFacade.fetchAllOffersSaveAllIfNotExists();
        assertThat(response.size()).isEqualTo(4);
    }

    @Test
    public void should_return_empty_list_when_no_new_offers() {
        OfferList offerList = new OfferList(
                List.of()
        );
        OfferFacade offerFacade = new OfferFacadeTestConfiguration(offerList).offerFacadeForTests();
        List<OfferDto> response = offerFacade.fetchAllOffersSaveAllIfNotExists();
        assertThat(response.size()).isEqualTo(0);
    }

}
