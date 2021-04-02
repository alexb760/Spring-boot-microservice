package microservice.book.gamification.game;

import java.util.List;
import lombok.Value;
import microservice.book.gamification.challenge.ChallengeSolvedDTO;
import microservice.book.gamification.challenge.ChallengeSolvedEvent;
import microservice.book.gamification.game.domain.BadgeType;

/**
 * Base on an attempt it compute the stores and the badges then stores them
 * this is planed to be accesible from Multiplication service via a controller name {@code #GameController}
 *
 * @author Alexander Bravo
 */
public interface GameService {
    /**
     * Process a new attempt from a given user.
     *
     * @param challenge the challenge data with user details, factors, etc.
     * @return a {@link GameResult} object containing the new score and badge cards obtained
     */
    GameResult newAttemptForUser(ChallengeSolvedDTO challenge);

    /**
     * process a new attempt for a given user but this event is received from RabbitMQ messages brocker
     * @param challenge event
     * @return result
     */
    GameResult newAttemptForUser(ChallengeSolvedEvent challenge);

    @Value
    class GameResult {
        int score;
        List<BadgeType> badges;
    }
}
