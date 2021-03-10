package microservice.book.gamification.game.badgeprocessors;

import java.util.List;
import java.util.Optional;
import microservice.book.gamification.challenge.ChallengeSolvedDTO;
import microservice.book.gamification.game.domain.BadgeType;
import microservice.book.gamification.game.domain.ScoreCard;
import org.springframework.stereotype.Component;

/**
 * @author Alexander Bravo
 */
@Component
public class BronzeBadgeProcessor implements BadgeProcessor{

    @Override
    public Optional<BadgeType> processForOptionalBadge(
        int currentScore,
        List<ScoreCard> scoreCardList,
        ChallengeSolvedDTO solved) {
        return currentScore > 50 ?
            Optional.of(BadgeType.BRONZE) :
            Optional.empty();
    }

    @Override
    public BadgeType badgeType() {
        return BadgeType.BRONZE;
    }
}
