package microservice.book.gamification.game.domain;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Value;
import lombok.With;

/**
 * @author Alexander Bravo
 */
@Value
@AllArgsConstructor
public class LeaderBoardRow {
    Long userId;
    Long totalScore;
    @With
    List<String> badges;
    public LeaderBoardRow(final Long userId, final Long totalScore) {
        this.userId = userId;
        this.totalScore = totalScore;
        this.badges = List.of();
    }
}
