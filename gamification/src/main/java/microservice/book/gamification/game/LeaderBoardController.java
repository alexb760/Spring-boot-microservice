package microservice.book.gamification.game;

import java.util.List;
import lombok.RequiredArgsConstructor;
import microservice.book.gamification.game.domain.LeaderBoardRow;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * This class implements a REST API for the Gamification LeaderBoard service.
 *
 * @author Alexander Bravo
 */
@RestController
@RequestMapping("/leaders")
@RequiredArgsConstructor
class LeaderBoardController {
    private final LeaderBoardService leaderBoardService;

    @GetMapping
    public List<LeaderBoardRow> getLeaderBoard() {
        return leaderBoardService.getCurrentLeaderBoard();
    }
}
