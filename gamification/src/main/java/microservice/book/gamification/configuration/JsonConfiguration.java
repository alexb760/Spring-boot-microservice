package microservice.book.gamification.configuration;

import com.fasterxml.jackson.databind.Module;
import com.fasterxml.jackson.datatype.hibernate5.Hibernate5Module;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author Alexander Bravo
 */
@Configuration
public class JsonConfiguration {
    @Bean
    public Module hibernateModule() {
        return new Hibernate5Module();
    }
}
