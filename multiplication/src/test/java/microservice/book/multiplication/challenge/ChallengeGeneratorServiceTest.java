package microservice.book.multiplication.challenge;

import static org.assertj.core.api.BDDAssertions.then;
import java.util.Random;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

/**
 * @author Alexander Bravo
 */
@ExtendWith(MockitoExtension.class)
public class ChallengeGeneratorServiceTest {
    private ChallengeGeneratorService challengeGeneratorService;
    //We use SPY to stub an Object since we need to replace teh behavior of the object
    // not of the class itself.
    @Spy
    Random random;

    @BeforeEach
    public void setUp(){
        challengeGeneratorService = new ChallengeGeneratorServiceImpl(random);
    }

    @Test
    public void generateRandomFactorIsBetweenExpectedLimits(){
        BDDMockito.given(random.nextInt(89)).willReturn(20, 30);
        Challenge challenge = challengeGeneratorService.randomChallenge();
        then(challenge).isEqualTo(new Challenge(31, 41));
    }
}
