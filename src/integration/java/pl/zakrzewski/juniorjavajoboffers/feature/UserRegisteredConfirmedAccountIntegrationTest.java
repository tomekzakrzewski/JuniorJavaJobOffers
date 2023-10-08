package pl.zakrzewski.juniorjavajoboffers.feature;

import com.github.tomakehurst.wiremock.client.WireMock;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import pl.zakrzewski.juniorjavajoboffers.BaseIntegrationTest;
import pl.zakrzewski.juniorjavajoboffers.domain.offer.OfferFetchable;

public class UserRegisteredConfirmedAccountIntegrationTest extends BaseIntegrationTest {

    @Autowired
    OfferFetchable offerFetchable;

    @Test
    public void should_user_register_and_confirm_account_and_system_send_job_offers() {

        wireMockServer.stubFor(WireMock.post("/")
                .withRequestBody(WireMock.matching(".*"))
                .willReturn(WireMock.aResponse()
                        .withStatus(HttpStatus.OK.value())
                        .withHeader("Content-Type", "application/json")
                        .withBody("""
                                                                {
                                  "postings": [
                                    {
                                      "name": "Some startp",
                                      "location": {
                                        "fullyRemote": true
                                      },
                                      "title": "Junior Java Developer ",
                                      "url": "junior-java-developer-startup",
                                      "salary": {
                                        "from": 7000,
                                        "to": 9000
                                      }
                                    },
                                    {
                                      "name": "I love programming",
                                      "location": {
                                        "fullyRemote": true
                                      },
                                      "title": "Junior Software Engineer",
                                      "url": "java-software-engineer-i-love-programming",
                                      "salary": {
                                        "from": 5000,
                                        "to": 9000
                                      }
                                    },
                                    {
                                      "name": "Java world",
                                      "location": {
                                        "fullyRemote": false
                                      },
                                      "title": "Junior Java Developer",
                                      "url": "junior-java-developer-java-world",
                                      "salary": {
                                        "from": 6000,
                                        "to": 8000
                                      }
                                    }
                                  ]
                                }
                                                                                                """)
                )
        );

        offerFetchable.fetchOffersFromNofluffjobs();
    }
}