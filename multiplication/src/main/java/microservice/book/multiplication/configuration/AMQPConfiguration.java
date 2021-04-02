package microservice.book.multiplication.configuration;

import org.springframework.amqp.core.ExchangeBuilder;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Configures RabbitMQ via AMQP abstraction to use events in our application.
 *
 * @author Alexander Bravo
 */

@Configuration
public class AMQPConfiguration {

    /**
     * We have made our topic durable it means messages will remain even after restart rabbitMQ
     * also we declared Topic Exchange:
     * <p>
     *     is the most flexible. Instead of binding queues to this exchange using a given value,
     *     we can use a pattern. That allows subscribers to register queues to consume a filtered set of messages.
     *     Patterns can use # to match any set of words or * to match only one word.
     * </p>
     * other possibles configurations:
     * <p>
     *     <ul>
     *         <li>
     *             The default exchange is predeclared by the broker.
     *             All created queues are bound to this exchange with a binding key equal to the queue name.
     *             From a conceptual perspective, it means that messages can be published with a destination queue in mind if we use that name as a routing key.
     *             Technically, these messages still go through the exchange. This setup is not commonly used since it defeats the whole routing purpose.
     *         </li>
     *         <li>
     *             The direct exchange is commonly used for unicast routing.
     *             The difference with the Default Exchange is that you can use your own binding keys,
     *             and you can also create multiple queues with the same binding key. Then,
     *             these queues will all get messages whose routing key matches the binding key. Conceptually,
     *             we use it when we publish messages knowing the destination (unicast), but we don’t need to know how many queues will get the message.
     *         </li>
     *         <li>
     *             The fanout exchange doesn’t use routing keys.
     *             It routes all the messages to all the queues that are bound to the exchange,
     *             so it’s perfect for a broadcast scenario.
     *         </li>
     *         <li>
     *             The headers exchange uses the message headers as routing keys for better flexibility
     *             since we can set up the match condition to one or many headers and for an all-match or any-match configuration.
     *             Standard routing keys are therefore ignored.
     *         </li>
     *     </ul>
     * </p>
     *
     * @param exchangeName topic name got from resources.properties thanks to @Value argument from Spring
     * @return an instances of {@link TopicExchange}
     */
    @Bean
    public TopicExchange challengesTopicExchange(@Value("${amqp.exchange.attempts}") final String exchangeName){
//        return ExchangeBuilder.directExchange(exchangeName).durable(false).build();
//        return ExchangeBuilder.fanoutExchange(exchangeName).durable(true).build();
//        return ExchangeBuilder.headersExchange(exchangeName).durable(true).build();
        return ExchangeBuilder.topicExchange(exchangeName).durable(true).build();
    }

    /**
     * Prepare aur messages into a Json response avoiding the java serialization and make it lose couple
     * and it can be compatible with other services written in different languages
     *
     * @return new instance of {@link Jackson2JsonMessageConverter}
     */
    @Bean
    Jackson2JsonMessageConverter producerJackson2MessageConverter(){
        return new Jackson2JsonMessageConverter();
    }
}
