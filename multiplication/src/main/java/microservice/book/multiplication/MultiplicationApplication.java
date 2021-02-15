package microservice.book.multiplication;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class MultiplicationApplication {

	public static void main(String[] args) {
		SpringApplication.run(MultiplicationApplication.class, args);
	}

	/**
	 * Overriding beans :
	 *
	 * By providing a new Object mapper configuration we are telling to the jackson serialization
	 * that the new property will be in camel case
	 * <p>
	 *{
	 *     "factor_a": 35,
	 *     "factor_b": 65
	 * }
	 *
	 * @return new ObjectMapper configuration to jackson serialization
	 */
//	@Bean
//	public ObjectMapper objectMapper() {
//		var om = new ObjectMapper();
//		om.setPropertyNamingStrategy(PropertyNamingStrategy.SNAKE_CASE);
//		return om;
//	}

}
