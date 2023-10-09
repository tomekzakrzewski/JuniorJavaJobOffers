package pl.zakrzewski.juniorjavajoboffers.infrastructure.offer.http;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;
import pl.zakrzewski.juniorjavajoboffers.domain.offer.CriteriaSearch;
import pl.zakrzewski.juniorjavajoboffers.domain.offer.OfferFetchable;

import java.time.Duration;

@Configuration
public class OfferHttpClientConfig {

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplateBuilder()
                .setConnectTimeout(Duration.ofMillis(1000))
                .setReadTimeout(Duration.ofMillis(5000))
                .build();
    }

    @Bean
    public CriteriaSearch criteriaSearch() {
        CriteriaSearch criteriaSearch = new CriteriaSearch();
        criteriaSearch.setRequirement(new String[] {"Java"});
        criteriaSearch.setSeniority(new String[] { "trainee", "junior" });
        return criteriaSearch;
    }

    @Bean
    public OfferFetchable remoteOfferClient(RestTemplate restTemplate, CriteriaSearch criteriaSearch,
                                            @Value("${offer.http.client.config.uri}") String uri) {
        return new OfferHttpClient(restTemplate, criteriaSearch, uri);
    }
}
