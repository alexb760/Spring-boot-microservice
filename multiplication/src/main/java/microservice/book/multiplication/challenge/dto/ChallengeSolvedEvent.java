package microservice.book.multiplication.challenge.dto;

import lombok.Value;

/**
 * This DTO class will transfer data to the Messages broker.
 *
 * @author Alexander Bravo
 */

@Value
public class ChallengeSolvedEvent {
    long attemptId;
    boolean correct;
    int factorA;
    int factorB;
    long userId;
    String userAlias;
}
