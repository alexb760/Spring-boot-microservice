package microservice.book.gamification.game;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import microservice.book.gamification.challenge.ChallengeSolvedDTO;
import org.springframework.amqp.AmqpRejectAndDontRequeueException;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

/**
 * @author Alexander Bravo
 */
@RequiredArgsConstructor
@Slf4j
@Service
public class GameEventHandler {
    private final GameService gameService;
    //Subscribe to the RabbitMQ Queue
    @RabbitListener(queues = "${amqp.queue.gamification}")
    void handleMultiplicationSolved(final ChallengeSolvedDTO event){
        log.info("Challenge solve received [{}]", event);
        try {
            gameService.newAttemptForUser(event);
        } catch (Exception e) {
            //Our strategy is send an AUTO ACK to the brocker if everything is ok, if no
            //log the error and throws and AmqpRejectAndDontRequeueException.
            //This is a shortcut provided by Spring AMQP to reject the message and tell the broker not to requeue it.
            log.error("OH! no, something happens while processing the event", e);
            //Avoid requeue the event and reprocessed
            throw new AmqpRejectAndDontRequeueException(e);
        }
    }
}
