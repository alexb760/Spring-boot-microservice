package microservice.book.multiplication.user.repository;

import java.util.List;
import java.util.Optional;
import microservice.book.multiplication.user.User;
import org.springframework.data.repository.CrudRepository;

/**
 * @author Alexander Bravo
 */
public interface UserRepository extends CrudRepository<User, Long> {
    Optional<User> findByAlias(String userAlias);
    List<User> findByIdIn(final Iterable<Long> ids);
}
