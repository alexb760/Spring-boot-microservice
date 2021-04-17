package microservice.book.logs.configuaration;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.ExchangeBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.QueueBuilder;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/** @author Alexander Bravo */
@Configuration
public class AMQPConfiguration {

  @Bean
  public TopicExchange logsExchange(@Value("${amqp.exchange.logsback}") final String exchangeName) {
    return ExchangeBuilder.topicExchange(exchangeName).durable(true).build();
  }

  @Bean
  public Queue logsQueue() {
    return QueueBuilder.durable("logs.queue").build();
  }

  @Bean
  public Binding logsBinding(Queue logsQueue, TopicExchange logsExchange) {
    return BindingBuilder.bind(logsQueue).to(logsExchange).with("#");
  }
}
