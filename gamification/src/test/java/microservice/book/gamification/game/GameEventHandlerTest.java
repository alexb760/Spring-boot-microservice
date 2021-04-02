package microservice.book.gamification.game;

import microservice.book.gamification.challenge.ChallengeSolvedDTO;
import org.assertj.core.api.BDDAssertions;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.amqp.AmqpRejectAndDontRequeueException;

/**
 * @author Alexander Bravo
 */
@ExtendWith(MockitoExtension.class)
public class GameEventHandlerTest {
    private GameEventHandler eventHandler;
    @Mock
    private GameService gameService;

    @BeforeEach
    public void setup(){
        eventHandler = new GameEventHandler(gameService);
    }

    @Test
    public void testReceivedEventOk(){
        //given
        ChallengeSolvedDTO event = new ChallengeSolvedDTO(1L, true, 12, 42, 1L, "test");
        //when
        eventHandler.handleMultiplicationSolved(event);
        //then
        Mockito.verify(gameService).newAttemptForUser(event);
    }

    @Test
    public void testRecievedEventException(){
        //given
        ChallengeSolvedDTO event = new ChallengeSolvedDTO(1L, true, 12, 42, 1L, "test");
        BDDMockito.given(gameService.newAttemptForUser(event))
            .willThrow(AmqpRejectAndDontRequeueException.class);
        //When
        Exception exception = Assertions.assertThrows(
            AmqpRejectAndDontRequeueException.class,
            () -> eventHandler.handleMultiplicationSolved(event)
        );
        //then
        BDDAssertions.then(exception).isNotNull();
    }
}
