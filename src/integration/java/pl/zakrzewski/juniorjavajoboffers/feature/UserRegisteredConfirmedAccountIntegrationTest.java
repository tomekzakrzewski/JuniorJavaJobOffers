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
import pl.zakrzewski.juniorjavajoboffers.domain.offer.OfferFetchable;
import pl.zakrzewski.juniorjavajoboffers.domain.register.dto.RegisterRequestDto;
import pl.zakrzewski.juniorjavajoboffers.domain.register.dto.RegistrationResultDto;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class UserRegisteredConfirmedAccountIntegrationTest extends BaseIntegrationTest implements SampleOfferResponse {

    @Autowired
    OfferFetchable offerFetchable;

    @Test
    public void should_user_register_and_confirm_account_and_system_send_job_offers() throws Exception {

        // step 1: user made POST to /register with username=Tomek and email=tomek@gmail.com and recived confirmation mail
        RegisterRequestDto request = new RegisterRequestDto("Tomek", "tomek@gmail.com");
        ResultActions successRegisterRequest = mockMvc.perform(post("/api/v1/registration")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)));


        MvcResult mvcResult = successRegisterRequest.andExpect(status().isCreated()).andReturn();
        String json = mvcResult.getResponse().getContentAsString();
        RegistrationResultDto registrationResultDto = objectMapper.readValue(json, RegistrationResultDto.class);
        String token = registrationResultDto.token();
        assertThat(greenMail.waitForIncomingEmail(5000, 1)).isTrue();

        // step 2: user made GET to /registration?token= with generated token and confirmed account
        ResultActions successConfirmToken = mockMvc.perform(get("/api/v1/registration?token=" + token)
                .contentType(MediaType.APPLICATION_JSON));
        MvcResult confirmTokenResult = successConfirmToken.andExpect(status().isOk()).andReturn();


        // step 3: external service returns job offers
        wireMockServer.stubFor(WireMock.post("/")
                .withRequestBody(WireMock.matching(".*"))
                .willReturn(WireMock.aResponse()
                        .withStatus(HttpStatus.OK.value())
                        .withHeader("Content-Type", "application/json")
                        .withBody(bodyWithThreeOffersJson())
                )
        );



        // step 4: system fetches job offers and sends email


        //step  : user made POST to /unsubscribe with ID and unsubscribed
        String userId = registrationResultDto.id();
        ResultActions successUnsubscribe = mockMvc.perform(post("/api/v1/registration/unsubscribe?id=" + userId)
                .contentType(MediaType.APPLICATION_JSON));

        MvcResult mvcResult2 = successUnsubscribe.andExpect(status().isNoContent()).andReturn();



    }
}