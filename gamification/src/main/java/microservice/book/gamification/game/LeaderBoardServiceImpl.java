package microservice.book.gamification.game;

import java.util.List;
import java.util.function.Function;
import java.util.function.UnaryOperator;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import microservice.book.gamification.game.domain.LeaderBoardRow;
import org.springframework.stereotype.Service;

/**
 * @author Alexander Bravo
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class LeaderBoardServiceImpl implements LeaderBoardService{

    private final ScoreRepository scoreRepository;
    private final BadgeRepository badgeRepository;

    @Override
    public List<LeaderBoardRow> getCurrentLeaderBoard() {
        // Get score only
        List<LeaderBoardRow> scoreOnly = scoreRepository.findFirst10();
        // Combine with badges
        return scoreOnly.stream().map(getBoardRowFunction()).collect(Collectors.toList());
    }

    /**
     * Extract the method from the #getCurrentLeaderBoard to increase readability
     *
     * @return LeaderBoardRow object.
     */
    private UnaryOperator<LeaderBoardRow> getBoardRowFunction() {
        return row -> {
            List<String> badges = badgeRepository.
                findByUserIdOrderByBadgeTimestampDesc(row.getUserId())
                .stream()
                .map(b -> b.getBadgeType().getDescription())
                .collect(Collectors.toList());
            return row.withBadges(badges);
        };
    }
}
