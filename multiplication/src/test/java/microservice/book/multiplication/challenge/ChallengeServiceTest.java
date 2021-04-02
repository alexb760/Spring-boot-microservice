package microservice.book.multiplication.challenge;

import static org.assertj.core.api.BDDAssertions.then;
import static org.mockito.AdditionalAnswers.returnsFirstArg;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import microservice.book.multiplication.challenge.repository.ChallengeAttemptRepository;
import microservice.book.multiplication.serviceclients.GamificationServiceClient;
import microservice.book.multiplication.user.User;
import microservice.book.multiplication.user.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

/**
 * @author Alexander Bravo
 */
@ExtendWith(MockitoExtension.class)
public class ChallengeServiceTest {
    private ChallengeService challengeService;

    //Adding the new repositories to create de test before adding the logic to the service.
    // This following the TDD principle.
    @Mock
    private ChallengeAttemptRepository attemptRepository;
    @Mock
    private UserRepository userRepository;
    @Mock
    private GamificationServiceClient gameClient;
    @Mock
    private ChallengeEventPublisher eventPublisher;

    @BeforeEach
    public void setUp(){
        challengeService = new ChallengeServiceImpl(attemptRepository, userRepository, eventPublisher);
    }

    @Test
    public void checkCorrectAttemptTest() {
        // given
        ChallengeAttemptDTO attemptDTO = new ChallengeAttemptDTO(50, 60, "john_doe", 3000);
        given(attemptRepository.save(any())).will( returnsFirstArg());
        // when
        ChallengeAttempt resultAttempt = challengeService.verifyAttempt(attemptDTO);
        // then
        then(resultAttempt.isCorrect()).isTrue();
        verify(userRepository).save(new User("john_doe"));
        verify(attemptRepository).save(resultAttempt);
//        verify(gameClient).sendAttempt(resultAttempt);
    }

    @Test
    public void checkExistingUserTest() {
        // given
        User existingUser = new User(1L, "john_doe");
        given(attemptRepository.save(any())).will( returnsFirstArg());
        given(userRepository.findByAlias("john_doe"))
            .willReturn(Optional.of(existingUser));

        ChallengeAttemptDTO attemptDTO = new ChallengeAttemptDTO(50, 60, "john_doe", 5000);
        // when
        ChallengeAttempt resultAttempt = challengeService.verifyAttempt(attemptDTO);
        // then
        then(resultAttempt.isCorrect()).isFalse();
        then(resultAttempt.getUser()).isEqualTo(existingUser);
        verify(userRepository, never()).save(any());
        verify(attemptRepository).save(resultAttempt);
//        verify(gameClient).sendAttempt(resultAttempt);
    }

    @Test
    public void checkLastAttemptTest(){
        User expectedUser = new User("John_doe");
        ChallengeAttempt expectedAttempt = new ChallengeAttempt(1L, expectedUser, 50, 60, 3000, true);
        //given
        given(attemptRepository.lastAttempts(expectedUser.getAlias()))
            .willReturn(Collections.singletonList(expectedAttempt));

        //when
        List<ChallengeAttempt> actual =  challengeService.lastAttempt(expectedUser.getAlias());

        //Then
        then(actual).hasSize(1);
        then(actual.get(0).getUser().getAlias()).isEqualTo(expectedUser.getAlias());
        verify(attemptRepository).lastAttempts(expectedUser.getAlias());
    }

    @Test
    public void checkWrongAttemptTest() {
        // given
        ChallengeAttemptDTO attemptDTO =
            new ChallengeAttemptDTO(50, 60, "john_doe", 5000);
        // when
        ChallengeAttempt resultAttempt =
            challengeService.verifyAttempt(attemptDTO);
        // then
        then(resultAttempt).isNull();
    }

    @Test
    public void retrieveStatsTest() {
        // given
        User user = new User("john_doe");
        ChallengeAttempt attempt1 = new ChallengeAttempt(1L, user, 50, 60, 3010, false);
        ChallengeAttempt attempt2 = new ChallengeAttempt(2L, user, 50, 60, 3051, false);
        List<ChallengeAttempt> lastAttempts = List.of(attempt1, attempt2);
        given(attemptRepository.findTop10ByUserAliasOrderByIdDesc("john_doe"))
            .willReturn(lastAttempts);

        // when
        List<ChallengeAttempt> latestAttemptsResult =
            challengeService.getStatsForUser("john_doe");

        // then
        then(latestAttemptsResult).isEqualTo(lastAttempts);
    }
}
