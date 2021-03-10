package microservice.book.gamification.game.badgeprocessors;

import java.util.List;
import java.util.Optional;
import microservice.book.gamification.challenge.ChallengeSolvedDTO;
import microservice.book.gamification.game.domain.BadgeType;
import microservice.book.gamification.game.domain.ScoreCard;

/**
 * Lucky factor will occur only if a factor is equal to {@code LUCKY_FACTO = 42}
 *
 * @author Alexander Bravo
 */
public class LuckyNumberBadgeProcessor implements BadgeProcessor{

    private static final int LUCKY_FACTOR = 42;

    @Override
    public Optional<BadgeType> processForOptionalBadge(
        int currentScore, List<ScoreCard> scoreCardList, ChallengeSolvedDTO solved) {
        return solved.getFactorA() == LUCKY_FACTOR || solved.getFactorB() == LUCKY_FACTOR ?
            Optional.of(BadgeType.LUCKY_NUMBER)  : Optional.empty();
    }

    @Override
    public BadgeType badgeType() {
        return BadgeType.LUCKY_NUMBER;
    }
}
