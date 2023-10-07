package pl.zakrzewski.juniorjavajoboffers.infrastructure.offer.http;

import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import pl.zakrzewski.juniorjavajoboffers.domain.offer.CriteriaSearch;
import pl.zakrzewski.juniorjavajoboffers.domain.offer.OfferFetchable;
import pl.zakrzewski.juniorjavajoboffers.domain.offer.OfferList;
import pl.zakrzewski.juniorjavajoboffers.domain.offer.OfferRequest;
import pl.zakrzewski.juniorjavajoboffers.domain.offer.dto.OfferResponse;

import java.util.Collections;
import java.util.List;

@AllArgsConstructor
@Log4j2
public class OfferHttpClient implements OfferFetchable {

    private final RestTemplate restTemplate;
    private final CriteriaSearch criteriaSearch;
    private final String uri;
    public List<OfferResponse> fetchOffersFromNofluffjobs() {
        log.info("Started fetching offers from NoFluffJobs");
        HttpHeaders headers = new HttpHeaders();
        OfferRequest offerRequest = new OfferRequest(criteriaSearch);
        HttpEntity<OfferRequest> request = new HttpEntity<>(offerRequest, headers);
        ResponseEntity<OfferList> response = restTemplate.exchange(
                uri,
                HttpMethod.POST,
                request,
                new ParameterizedTypeReference<>() {}
        );

        final OfferList body = response.getBody();
        if (body == null) {
            log.info("Response body was null, returning empty list");
            return Collections.emptyList();
        }
        log.info("Success");
        return body.getOffers();
    }

}
