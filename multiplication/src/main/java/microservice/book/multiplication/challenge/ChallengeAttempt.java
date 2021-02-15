package microservice.book.multiplication.challenge;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

/**
 * @author Alexander Bravo
 */
@Getter
@ToString
@EqualsAndHashCode
@AllArgsConstructor
public class ChallengeAttempt {
    private final Long id;
    private final Long userId;
    private final int factorA;
    private final int factorB;
    private  final int resultAttempt;
    private final boolean correct;
}
