package microservice.book.gamification.game.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * @author Alexander Bravo
 */
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class BadgeCard {

    @Id
    @GeneratedValue
    private Long badgeId;
    private Long userId;
    @EqualsAndHashCode.Exclude
    private long badgeTimestamp;
    private BadgeType badgeType;

    public BadgeCard(final Long userId, final BadgeType badgeType) {
        this(null, userId, System.currentTimeMillis(), badgeType);
    }
}