package microservice.book.multiplication.configuration;

import com.fasterxml.jackson.databind.Module;
import com.fasterxml.jackson.datatype.hibernate5.Hibernate5Module;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author Alexander Bravo
 */
@Configuration
public class JsonConfiguration {

    /**
     * This configuration is useful when we face issues whit LAZY Hibernate annotation which try to
     * map the adjacent table in this case User table. However, this Bean is empty or null at that moment.
     * Now we add FasterXML library an create a new BEAN telling hibernate about what we want to do now
     *
     * @return Hibernate Module Bean
     */
    @Bean
    public Module hibernateMudule(){
        return new Hibernate5Module();
    }
}
