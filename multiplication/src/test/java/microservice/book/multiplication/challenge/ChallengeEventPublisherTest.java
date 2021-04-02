package microservice.book.multiplication.challenge;

import microservice.book.multiplication.challenge.dto.ChallengeSolvedEvent;
import microservice.book.multiplication.user.User;
import org.assertj.core.api.BDDAssertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.amqp.core.AmqpTemplate;

/**
 * @author Alexander Bravo
 */
@ExtendWith(MockitoExtension.class)
public class ChallengeEventPublisherTest {
    private ChallengeEventPublisher eventPublisher;
    @Mock
    private AmqpTemplate amqpTemplate;

    @BeforeEach
    public void setup(){
        eventPublisher = new ChallengeEventPublisher(amqpTemplate, "test.topic");
    }

    @ParameterizedTest
    @ValueSource(booleans = {true, false})
    public void testSendAttempt(boolean status){
        //given
        ChallengeAttempt attempt = createTestAttempt(status);
        //when
        eventPublisher.challengeSolved(attempt);
        //then
        var exchangeCaptop = ArgumentCaptor.forClass(String.class);
        var routinKeyCaptor = ArgumentCaptor.forClass(String.class);
        var eventCaptor = ArgumentCaptor.forClass(ChallengeSolvedEvent.class);

        Mockito.verify(amqpTemplate).convertAndSend(
            exchangeCaptop.capture(), routinKeyCaptor.capture(), eventCaptor.capture());

        BDDAssertions.then(exchangeCaptop.getValue()).isEqualTo("test.topic");
        BDDAssertions.then(routinKeyCaptor.getValue()).isEqualTo(
            "attempt." + (status ? "correct" : "wrong"));
        BDDAssertions.then(eventCaptor.getValue()).isEqualTo(solvedEvent(status));
    }

    private ChallengeAttempt createTestAttempt(boolean correct) {
        return new ChallengeAttempt(1L, new User(10L, "john"), 30, 40,
            correct ? 1200 : 1300, correct);
    }
    private ChallengeSolvedEvent solvedEvent(boolean correct) {
        return new ChallengeSolvedEvent(1L, correct, 30, 40, 10L, "john");
    }
}
