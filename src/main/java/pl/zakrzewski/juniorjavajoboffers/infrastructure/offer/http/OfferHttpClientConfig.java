package pl.zakrzewski.juniorjavajoboffers.infrastructure.offer.http;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;
import pl.zakrzewski.juniorjavajoboffers.domain.offer.CriteriaSearch;

@Configuration
public class OfferHttpClientConfig {

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

    @Bean
    public CriteriaSearch criteriaSearch() {
        CriteriaSearch criteriaSearch = new CriteriaSearch();
        criteriaSearch.setRequirement(new String[] {"Java"});
        criteriaSearch.setSeniority(new String[] { "trainee", "junior" });
        return criteriaSearch;
    }
}
