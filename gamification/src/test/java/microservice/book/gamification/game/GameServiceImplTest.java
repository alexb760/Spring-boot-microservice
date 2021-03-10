package microservice.book.gamification.game;

import static org.assertj.core.api.BDDAssertions.then;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import java.util.List;
import java.util.Optional;
import microservice.book.gamification.challenge.ChallengeSolvedDTO;
import microservice.book.gamification.game.badgeprocessors.BadgeProcessor;
import microservice.book.gamification.game.domain.BadgeCard;
import microservice.book.gamification.game.domain.BadgeType;
import microservice.book.gamification.game.domain.ScoreCard;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

/**
 * @author Alexander Bravo
 */
@ExtendWith(MockitoExtension.class)
public class GameServiceImplTest {
    private GameServiceImpl gameService;
    @Mock
    private ScoreRepository scoreRepository;
    @Mock
    private BadgeRepository badgeRepository;
    @Mock
    private BadgeProcessor badgeProcessor;

    @BeforeEach
    public void setup(){
        gameService = new GameServiceImpl(scoreRepository, badgeRepository, List.of(badgeProcessor));
    }

    @Test
    public void processCorrectAttemptTest(){
        // given
        long userId = 1L, attemptId = 10L;
        var attempt = new ChallengeSolvedDTO(attemptId, true, 20, 70, userId, "john");
        ScoreCard scoreCard = new ScoreCard(userId, attemptId);
        given(scoreRepository.getTotalScoreForUser(userId))
            .willReturn(Optional.of(10));
        given(scoreRepository.findByUserIdOrderByScoreTimestampDesc(userId))
            .willReturn(List.of(scoreCard));
        given(badgeRepository.findByUserIdOrderByBadgeTimestampDesc(userId))
            .willReturn(List.of(new BadgeCard(userId, BadgeType.FIRST_WON)));
        given(badgeProcessor.badgeType()).willReturn(BadgeType.LUCKY_NUMBER);
        given(badgeProcessor.processForOptionalBadge(10, List.of(scoreCard), attempt))
            .willReturn(Optional.of(BadgeType.LUCKY_NUMBER));


        // when
        final GameService.GameResult gameResult = gameService.newAttemptForUser(attempt);

        // then - should score one card and win the badge LUCKY_NUMBER
        then(gameResult).isEqualTo(
            new GameService.GameResult(10,
                List.of(BadgeType.LUCKY_NUMBER))
        );
        verify(scoreRepository).save(scoreCard);
        verify(badgeRepository).saveAll(
            List.of(new BadgeCard(userId, BadgeType.LUCKY_NUMBER))
        );
    }

    @Test
    public void processWrongAttemptTest() {
        // when
        GameService.GameResult gameResult = gameService.newAttemptForUser(
            new ChallengeSolvedDTO(10L, false, 10, 10, 1L, "john")
        );

        // then - shouldn't score anything
        then(gameResult).isEqualTo(new GameService.GameResult(0, List.of()));
    }

}
