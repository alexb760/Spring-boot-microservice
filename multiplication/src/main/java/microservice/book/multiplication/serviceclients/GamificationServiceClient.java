package microservice.book.multiplication.serviceclients;

import lombok.extern.slf4j.Slf4j;
import microservice.book.multiplication.challenge.ChallengeAttempt;
import microservice.book.multiplication.challenge.ChallengeSolvedDTO;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

/**
 * Integration client between Gamification and Multiplication services
 * We use a default {@link RestTemplate} config injected by Spring boot since
 * both are base on Spring Boot.
 *
 * @author Alexander Bravo
 */
@Slf4j
@Service
public class GamificationServiceClient {

    private final RestTemplate restTemplate;
    private final String gamificationHostUrl;

    public GamificationServiceClient(
        final RestTemplateBuilder builder,
        @Value("${service.gamification.host}") final String gamificationHostUrl) {

        restTemplate = builder.build();
        this.gamificationHostUrl = gamificationHostUrl;

    }

    public boolean sendAttempt(final ChallengeAttempt attempt) {
        try {
            ChallengeSolvedDTO dto = new ChallengeSolvedDTO(
                attempt.getId(),
                attempt.isCorrect(),
                attempt.getFactorA(),
                attempt.getFactorB(),
                attempt.getUser().getId(),
                attempt.getUser().getAlias());

            log.info("Gamification service Payload: {}", dto);

            ResponseEntity<String> r = restTemplate.postForEntity(
                gamificationHostUrl + "/attempts", dto, String.class);

            log.info("Gamification service response: {}", r.getStatusCode());

            return r.getStatusCode().is2xxSuccessful();
        } catch (Exception e) {
            log.error("URL: {}", gamificationHostUrl + "/attempts");
            log.error("There was a problem sending the attempt.", e);
            return false;
        }
    }
}
