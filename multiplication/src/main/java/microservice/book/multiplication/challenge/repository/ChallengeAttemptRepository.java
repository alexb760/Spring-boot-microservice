package microservice.book.multiplication.challenge.repository;

import java.util.List;
import microservice.book.multiplication.challenge.ChallengeAttempt;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

/**
 * @author Alexander Bravo
 */
public interface ChallengeAttemptRepository  extends CrudRepository<ChallengeAttempt, Long> {

    /**
     * @return the last 10 attempts for a given user, identified by their alias.
     */
    List<ChallengeAttempt> findTop10ByUserAliasOrderByIdDesc(String aliasUser);

    /**
     * @return the last attempts for a given user, identified by their alias.
     */
    @Query("SELECT a FROM ChallengeAttempt a WHERE a.user.alias = ?1 ORDER BY a.id DESC")
    List<ChallengeAttempt> lastAttempts(String userAlias);
}
