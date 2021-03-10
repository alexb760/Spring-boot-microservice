package microservice.book.gamification.game.badgeprocessors;

import java.util.List;
import java.util.Optional;
import microservice.book.gamification.challenge.ChallengeSolvedDTO;
import microservice.book.gamification.game.domain.BadgeType;
import microservice.book.gamification.game.domain.ScoreCard;

/**
 * Gold Type will return for all score greater than 400
 *
 * @author Alexander Bravo
 */
public class GoldBadgeProcessor implements BadgeProcessor{
    @Override
    public Optional<BadgeType> processForOptionalBadge(
        int currentScore, List<ScoreCard> scoreCardList, ChallengeSolvedDTO solved) {
        return currentScore > 400 ?
            Optional.of(BadgeType.GOLD) : Optional.empty();
    }

    @Override
    public BadgeType badgeType() {
        return BadgeType.GOLD;
    }
}
