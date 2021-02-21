package microservice.book.multiplication.challenge;

import java.util.List;

/**
 * @author Alexander Bravo
 */
public interface ChallengeService {

    /**
     * Verifies if an attempt coming from the presentation layer is correct or not.
     *
     * @return the resulting ChallengeAttempt object
     */
    ChallengeAttempt verifyAttempt(ChallengeAttemptDTO resultAttempt);

    /**
     * retrieves latest attempt from an alias
     *
     * @param alias User alias
     * @return List of attempt
     */
    List<ChallengeAttempt> lastAttempt(String alias);

    /**
     * Retrieves the stat of an User most likely the top 10 attempts
     *
     * @param alias User Alias
     * @return List of attempts
     */
    List<ChallengeAttempt> getStatsForUser(String alias);
}
