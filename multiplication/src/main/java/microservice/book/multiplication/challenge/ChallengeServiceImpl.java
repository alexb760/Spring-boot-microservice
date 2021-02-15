package microservice.book.multiplication.challenge;

import microservice.book.multiplication.user.User;
import org.springframework.stereotype.Service;

/**
 * @author Alexander Bravo
 */

@Service
public class ChallengeServiceImpl implements ChallengeService{

    @Override
    public ChallengeAttempt verifyAttempt(ChallengeAttemptDTO attemptDTO) {
        // Check if the attempt is correct
        boolean isCorrect = attemptDTO.getGuess() ==
            attemptDTO.getFactorA() * attemptDTO.getFactorB();
        // We don't use identifiers for now
        User user = new User(null, attemptDTO.getUserAlias());
        // Builds the domain object. Null id for now.
        ChallengeAttempt checkedAttempt = new ChallengeAttempt(null,
            user.getId(),
            attemptDTO.getFactorA(),
            attemptDTO.getFactorB(),
            attemptDTO.getGuess(),
            isCorrect
        );
        return checkedAttempt;
    }
}
