package microservice.book.multiplication.challenge;

import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import microservice.book.multiplication.challenge.repository.ChallengeAttemptRepository;
import microservice.book.multiplication.serviceclients.GamificationServiceClient;
import microservice.book.multiplication.user.User;
import microservice.book.multiplication.user.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

/**
 * @author Alexander Bravo
 */
@Slf4j
@RequiredArgsConstructor
@Service
public class ChallengeServiceImpl implements ChallengeService{

    private final ChallengeAttemptRepository attemptRepository;
    private final UserRepository userRepository;
    //Inject gamification service
    private final GamificationServiceClient gameClient;

    @Override
    public ChallengeAttempt verifyAttempt(ChallengeAttemptDTO attemptDTO) {

        // Check if the user already exists for that alias, otherwise create it
        User user = userRepository.findByAlias(attemptDTO.getUserAlias())
            .orElseGet(() -> {
                log.info("Creating new user with alias {}",
                    attemptDTO.getUserAlias());
                return userRepository.save(
                    new User(attemptDTO.getUserAlias())
                );
            });

        // Check if the attempt is correct
        boolean isCorrect = attemptDTO.getGuess() ==
            attemptDTO.getFactorA() * attemptDTO.getFactorB();
        // We don't use identifiers for now
//        User user = new User(null, attemptDTO.getUserAlias());
        // Builds the domain object. Null id for now.
        ChallengeAttempt checkedAttempt = new ChallengeAttempt(null,
            user,
            attemptDTO.getFactorA(),
            attemptDTO.getFactorB(),
            attemptDTO.getGuess(),
            isCorrect
        );
        // Stores the attempt
        ChallengeAttempt storedAttempt = attemptRepository.save(checkedAttempt);

        // Sends the attempt to gamification and prints the response
        boolean status = gameClient.sendAttempt(storedAttempt);
        log.info("Gamification service response: {}", status);

        return checkedAttempt;
    }

    @Override
    public List<ChallengeAttempt> lastAttempt(String alias) {
        return attemptRepository.lastAttempts(alias);
    }

    @Override
    public List<ChallengeAttempt> getStatsForUser(String alias) {
        return attemptRepository.findTop10ByUserAliasOrderByIdDesc(alias);
    }
}
