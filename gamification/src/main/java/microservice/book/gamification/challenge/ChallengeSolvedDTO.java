package microservice.book.gamification.challenge;

import lombok.Value;

/**
 * @author Alexander Bravo
 */
@Value
public class ChallengeSolvedDTO {
    long attemptId;
    boolean correct;
    int factorA;
    int factorB;
    long userId;
    String userAlias;
}
