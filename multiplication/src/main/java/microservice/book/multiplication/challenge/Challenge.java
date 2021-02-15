package microservice.book.multiplication.challenge;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

/**
 * This class represents a Challenge to solve a Multiplication (a * b).
 *
 * @author Alexander Bravo
 */
@Getter
@ToString
@EqualsAndHashCode
@AllArgsConstructor
public class Challenge {
    private final int factorA;
    private final int factorB;
}
