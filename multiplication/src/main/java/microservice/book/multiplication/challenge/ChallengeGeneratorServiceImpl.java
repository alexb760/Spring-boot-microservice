package microservice.book.multiplication.challenge;

import java.util.Random;
import org.springframework.stereotype.Service;

/**
 * To instruct Spring to load this service implementation in the context, we annotate the class with
 * @Service. We can later inject this service into other layers by using the interface and not the
 * implementation. This way, we keep loose coupling since we could swap the implementation without
 * needing to change.
 *
 * @author Alexander Bravo
 */

@Service
public class ChallengeGeneratorServiceImpl implements ChallengeGeneratorService{
    private final Random random;
    private final static int MINIMUN_FACTOR = 11;
    private final static int MAXIMUN_FACTOR = 100;

    /**
     * Default constructor
     */
    ChallengeGeneratorServiceImpl() {
        this.random = new Random();
    }

    /**
     * This constructor is just to make this class testable by introduce a constructor
     * that accepts an argument
     *
     * @param random a random object
     */
    protected ChallengeGeneratorServiceImpl(final Random random){
        this.random = random;
    }

    private int next(){
        return random.nextInt(MAXIMUN_FACTOR - MINIMUN_FACTOR) + MINIMUN_FACTOR;
    }


    @Override
    public Challenge randomChallenge() {
        return new Challenge(next(), next());
    }

}
