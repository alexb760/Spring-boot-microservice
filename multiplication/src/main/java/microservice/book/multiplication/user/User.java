package microservice.book.multiplication.user;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

/**
 * Identify the user in a object
 *
 * @author Alexander Bravo
 */
@Getter
@ToString
@EqualsAndHashCode
@AllArgsConstructor
public class User {
    private final Long id;
    private final String alias;
}
