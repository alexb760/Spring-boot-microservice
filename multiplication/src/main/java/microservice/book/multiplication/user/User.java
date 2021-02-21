package microservice.book.multiplication.user;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Identify the user in a object
 * <p>
 * The second iteration of this class we add {@code @Entity} annotation to make it
 * mappeable from the Spring JPA
 *
 * @author Alexander Bravo
 */
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {
    @Id
    @GeneratedValue
    private Long id;
    private String alias;

    public User(final String userAlias){
        this(null, userAlias);
    }
}
