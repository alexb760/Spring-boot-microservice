package microservice.book.logs.AMQConsumer;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.Marker;
import org.slf4j.MarkerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Service;

/**
 * @author Alexander Bravo
 */
@Slf4j
@Service
public class LogsConsumer {

 @RabbitListener(queues = "${amqp.exchange.logsqueue}")
 public void logs(final String msg, @Header("level") final String level, @Header("amqp_appId") final String appId){
  Marker marker = MarkerFactory.getMarker(appId);
  switch (level){
   case "INFO" -> log.info(marker, msg);
   case "WARN" -> log.warn(marker, msg);
   case "ERROR" -> log.error(marker, msg);
  }
 }
}
