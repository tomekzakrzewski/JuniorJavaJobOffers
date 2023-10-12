package pl.zakrzewski.juniorjavajoboffers.feature;

import com.github.tomakehurst.wiremock.client.WireMock;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import pl.zakrzewski.juniorjavajoboffers.BaseIntegrationTest;
import pl.zakrzewski.juniorjavajoboffers.SampleOfferResponse;
import pl.zakrzewski.juniorjavajoboffers.domain.offer.OfferFacade;
import pl.zakrzewski.juniorjavajoboffers.domain.offer.OfferFetchable;
import pl.zakrzewski.juniorjavajoboffers.domain.offer.dto.OfferDto;
import pl.zakrzewski.juniorjavajoboffers.domain.offer.exceptions.NewOffersNotFound;
import pl.zakrzewski.juniorjavajoboffers.domain.register.dto.RegisterRequestDto;
import pl.zakrzewski.juniorjavajoboffers.domain.register.dto.RegisterResultDto;

import java.time.Duration;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.awaitility.Awaitility.await;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class UserRegisteredConfirmedAccountIntegrationTest extends BaseIntegrationTest implements SampleOfferResponse {
    private final static int TIMEOUT = 5000;

    @Autowired
    OfferFetchable offerFetchable;

    @Autowired
    OfferFacade offerFacade;

    @Test
    public void should_user_register_and_confirm_account_and_system_send_job_offers() throws Exception {

        // step 1: external service returns job offers
        wireMockServer.stubFor(WireMock.post("/")
                .withRequestBody(WireMock.matching(".*"))
                .willReturn(WireMock.aResponse()
                        .withStatus(HttpStatus.OK.value())
                        .withHeader("Content-Type", "application/json")
                        .withBody(bodyWithThreeOffersJson())
                )
        );


        // step 2: user made POST to /register with username=Tomek and email=tomek@gmail.com and recived confirmation mail
        RegisterRequestDto request = new RegisterRequestDto("Tomek", "tomek@gmail.com");
        ResultActions successRegisterRequest = mockMvc.perform(post("/api/v1/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)));


        MvcResult mvcResult = successRegisterRequest.andExpect(status().isCreated()).andReturn();
        String json = mvcResult.getResponse().getContentAsString();
        RegisterResultDto registerResultDto = objectMapper.readValue(json, RegisterResultDto.class);
        String token = registerResultDto.token();
        assertThat(greenMail.waitForIncomingEmail(TIMEOUT, 1)).isTrue();


        // step 3: user made GET to /registration?token= with generated token and confirmed account
        ResultActions successConfirmToken = mockMvc.perform(get("/api/v1/confirm?token=" + token)
                .contentType(MediaType.APPLICATION_JSON));
        MvcResult confirmTokenResult = successConfirmToken.andExpect(status().isOk()).andReturn();


        // step 4: system fetches job offers at given time
        await().atMost(20, TimeUnit.SECONDS)
                .pollInterval(Duration.ofSeconds(1L))
                .until(() -> {
                            try {
                                List<OfferDto> result = offerFacade.getAllNewOffers();
                                return !result.isEmpty();
                            } catch (NewOffersNotFound e) {
                                return false;
                            }
                        }
                );


        // step 5: system sends email with job offers to user at given time
        assertThat(greenMail.waitForIncomingEmail(TIMEOUT, 2)).isTrue();


        //step 6: user made POST to /unsubscribe with ID and unsubscribed
        String userId = registerResultDto.id();
        ResultActions successUnsubscribe = mockMvc.perform(post("/api/v1/unsubscribe?id=" + userId)
                .contentType(MediaType.APPLICATION_JSON));

        successUnsubscribe.andExpect(status().isNoContent());
    }
}