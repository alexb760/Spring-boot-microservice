package microservice.book.gamification.game.badgeprocessors;

import java.util.List;
import java.util.Optional;
import microservice.book.gamification.challenge.ChallengeSolvedDTO;
import microservice.book.gamification.game.domain.BadgeType;
import microservice.book.gamification.game.domain.ScoreCard;

/**
 * Silver badge processor will return any score grater than 150.
 *
 * @author Alexander Bravo
 */
public class SilverBadgeProcessor implements BadgeProcessor{
    @Override
    public Optional<BadgeType> processForOptionalBadge(
        int currentScore, List<ScoreCard> scoreCardList, ChallengeSolvedDTO solved) {
        return currentScore > 150 ?
            Optional.of(BadgeType.SILVER) : Optional.empty();
    }

    @Override
    public BadgeType badgeType() {
        return BadgeType.SILVER;
    }
}
