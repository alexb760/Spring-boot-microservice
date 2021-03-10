package microservice.book.gamification.game;

import static org.assertj.core.api.BDDAssertions.then;
import static org.mockito.BDDMockito.given;

import java.util.List;
import microservice.book.gamification.game.domain.BadgeCard;
import microservice.book.gamification.game.domain.BadgeType;
import microservice.book.gamification.game.domain.LeaderBoardRow;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

/**
 * @author Alexander Bravo
 */
@ExtendWith(MockitoExtension.class)
public class LeaderBoardServiceImplTest {
    private LeaderBoardService boardService;
    @Mock
    ScoreRepository scoreRepository;
    @Mock
    BadgeRepository badgeRepository;

    @BeforeEach
    public void setup(){
        boardService = new LeaderBoardServiceImpl(scoreRepository, badgeRepository);
    }

    @Test
    public void currentLeaderBoardTest(){
        //Rows
        LeaderBoardRow row1 = new LeaderBoardRow(1L, 10L);
        LeaderBoardRow row2 = new LeaderBoardRow(2L, 100L);
        //List badgeCars
        BadgeCard badgeCard = new BadgeCard(1L, 1L, System.currentTimeMillis(), BadgeType.FIRST_WON);
        BadgeCard badgeCard2 = new BadgeCard(2L, 2L, System.currentTimeMillis(), BadgeType.GOLD);
        //given
        given(scoreRepository.findFirst10()).willReturn(List.of(row1, row2));
        given(badgeRepository.findByUserIdOrderByBadgeTimestampDesc(1L)).willReturn(List.of(badgeCard));
        given(badgeRepository.findByUserIdOrderByBadgeTimestampDesc(2L)).willReturn(List.of(badgeCard2));

        //when
        List<LeaderBoardRow> result =  boardService.getCurrentLeaderBoard();
        //Then
        LeaderBoardRow expectedLeaderBoard =
            new LeaderBoardRow(1L, 10L, List.of(BadgeType.FIRST_WON.getDescription()));

        then(result).hasSize(2);
        then(result).contains(expectedLeaderBoard);
    }

}
