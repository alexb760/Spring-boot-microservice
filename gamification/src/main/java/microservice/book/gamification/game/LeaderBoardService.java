package microservice.book.gamification.game;

import java.util.List;
import microservice.book.gamification.game.domain.LeaderBoardRow;

/**
 * @author Alexander Bravo
 */
public interface LeaderBoardService {
    /**
     * @return the current leader board ranked from high to low score
     */
    List<LeaderBoardRow> getCurrentLeaderBoard();
}
