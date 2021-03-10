package microservice.book.gamification.game;

import java.util.List;
import microservice.book.gamification.game.domain.BadgeCard;
import org.springframework.data.repository.CrudRepository;

/**
 * @author Alexander Bravo
 */
public interface BadgeRepository extends CrudRepository<BadgeCard, Long> {

    /**
     * Retrieves all BadgeCards for a given user.
     *
     * @param userId the id of the user to look for BadgeCards
     * @return the list of BadgeCards, sorted by most recent.
     */
    List<BadgeCard> findByUserIdOrderByBadgeTimestampDesc(final Long userId);
}
