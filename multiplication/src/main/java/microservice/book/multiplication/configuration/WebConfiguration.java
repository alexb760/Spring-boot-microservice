package microservice.book.multiplication.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Is not longer required since we start using Spring Cloud - Gateway
 * and is more convenient handle this in the gateway module.
 * this class only remains here as a example on how we can handle cors access from spring as well
 *
 *
 * @author Alexander Bravo
 */
//@Configuration
public class WebConfiguration implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**").allowedOrigins("http://localhost:3000");
    }
}
