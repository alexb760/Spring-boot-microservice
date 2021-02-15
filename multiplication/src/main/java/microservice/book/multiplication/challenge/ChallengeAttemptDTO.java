package microservice.book.multiplication.challenge;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;
import lombok.Value;

/**
 * @author Alexander Bravo
 */
@Value
public class ChallengeAttemptDTO {
    @Min(1)
    @Max(100)
    int factorA, factorB;
    @NotBlank
    String userAlias;
    @Positive(message = "How could you possibly get a negative result here? Try again.")
    int guess;
}
