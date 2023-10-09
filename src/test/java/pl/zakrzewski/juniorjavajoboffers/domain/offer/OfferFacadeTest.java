package pl.zakrzewski.juniorjavajoboffers.domain.offer;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import pl.zakrzewski.juniorjavajoboffers.domain.emailsender.EmailSenderFacade;
import pl.zakrzewski.juniorjavajoboffers.domain.offer.dto.OfferDto;
import pl.zakrzewski.juniorjavajoboffers.domain.offer.dto.OfferResponse;
import pl.zakrzewski.juniorjavajoboffers.domain.register.RegisterFacade;
import pl.zakrzewski.juniorjavajoboffers.domain.register.dto.UserIdEmailDto;

import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.*;

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
                                new OfferResponse.Location(false), new OfferResponse.Salary(5000L, 6000L), 1696587756840L
                        ),
                        new OfferResponse("EFGH", "efgx", "efgh",
                                new OfferResponse.Location(false), new OfferResponse.Salary(5000L, 6000L), 1696587756840L
                        ),
                        new OfferResponse("IJKL", "ijkl", "ijkl",
                                new OfferResponse.Location(true), new OfferResponse.Salary(8000L, 9000L), 1696587756840L
                        ),
                        new OfferResponse("Google", "Junior Java developer", "https://google.com",
                                new OfferResponse.Location(true), new OfferResponse.Salary(8000L, 9000L), 1696587756840L
                        ),
                        new OfferResponse("Google", "Junior Java developer", "https://google.com",
                                new OfferResponse.Location(true), new OfferResponse.Salary(8000L, 9000L), 1696587756840L
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

    @Disabled
    @Test
    public void should_send_email_with_job_offers() {
        OfferFacade offerFacade = new OfferFacadeTestConfiguration().offerFacadeForTests();
        RegisterFacade registerFacade = mock(RegisterFacade.class);
        EmailSenderFacade emailSenderFacade = mock(EmailSenderFacade.class);
        List<OfferDto> offers = offerFacade.fetchAllOffersSaveAllIfNotExists();
        List<UserIdEmailDto> users = List.of(
                new UserIdEmailDto("tomek@gmail.com", UUID.randomUUID().toString())
        );

        when(registerFacade.findEmailsAndIdsOfConfirmedUsers()).thenReturn(users);
        offerFacade.sendEmailWithJobOffers();
        verify(emailSenderFacade).sendJobOffersEmail(offers, users);

    }
}
