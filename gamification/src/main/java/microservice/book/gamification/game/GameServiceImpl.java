package microservice.book.gamification.game;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import microservice.book.gamification.challenge.ChallengeSolvedDTO;
import microservice.book.gamification.challenge.ChallengeSolvedEvent;
import microservice.book.gamification.game.badgeprocessors.BadgeProcessor;
import microservice.book.gamification.game.domain.BadgeCard;
import microservice.book.gamification.game.domain.BadgeType;
import microservice.book.gamification.game.domain.ScoreCard;
import org.springframework.stereotype.Service;

/**
 * Since we use constructor injection in #GameServiceImpl with a list of BadgeProcessor objects,
 * Spring will find all the beans that implement this interface and pass them to us.
 * This is a flexible way of extending our game without interfering with other existing logic.
 * We just need to add new BadgeProcessor implementations and annotate them with @Component so they are
 * loaded in the Spring context.
 *
 * @author Alexander Bravo
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class GameServiceImpl implements GameService{

    private final ScoreRepository scoreRepository;
    private final BadgeRepository badgeRepository;
    // Spring injects all the @Component beans in this list
    private final List<BadgeProcessor> badgeProcessors;

    @Override
    public GameResult newAttemptForUser(final ChallengeSolvedDTO challenge) {
        // We give points only if it's correct
        log.info("--->> challenge {}", challenge);
        if (challenge.isCorrect()) {
            ScoreCard scoreCard = new ScoreCard(challenge.getUserId(), challenge.getAttemptId());
            scoreRepository.save(scoreCard);

            log.info("User {} scored {} points for attempt id {}",
                challenge.getUserAlias(), scoreCard.getScore(),
                challenge.getAttemptId());

            List<BadgeCard> badgeCards = processForBadges(challenge);
            return new GameResult(
                scoreCard.getScore(),
                badgeCards.stream().map(BadgeCard::getBadgeType)
                    .collect(Collectors.toList()));
        } else {
            log.info("Attempt id {} is not correct. " +
                    "User {} does not get score.",
                challenge.getAttemptId(),
                challenge.getUserAlias());
            return new GameResult(0, List.of());
        }
    }

    @Override
    public GameResult newAttemptForUser(ChallengeSolvedEvent challenge) {
        throw new UnsupportedOperationException("Not supported yet");
    }

    /**
     * Checks the total score and the different score cards obtained
     * to give new badges in case their conditions are met.
     */
    private List<BadgeCard> processForBadges(final ChallengeSolvedDTO solvedChallenge) {

        Optional<Integer> optTotalScore = scoreRepository.
            getTotalScoreForUser(solvedChallenge.getUserId());
        if (optTotalScore.isEmpty()) return Collections.emptyList();
        int totalScore = optTotalScore.get();
        // Gets the total score and existing badges for that user
        List<ScoreCard> scoreCardList = scoreRepository
            .findByUserIdOrderByScoreTimestampDesc(solvedChallenge.getUserId());
        Set<BadgeType> alreadyGotBadges = badgeRepository
            .findByUserIdOrderByBadgeTimestampDesc(solvedChallenge.getUserId())
            .stream()
            .map(BadgeCard::getBadgeType)
            .collect(Collectors.toSet());
        // Calls the badge processors for badges that the user doesn't have yet
        List<BadgeCard> newBadgeCards = badgeProcessors.stream()
            .filter(bp -> !alreadyGotBadges.contains(bp.badgeType()))
            .map(bp -> bp.processForOptionalBadge(totalScore, scoreCardList, solvedChallenge))
            .flatMap(Optional::stream) // returns an empty stream if empty
            // maps the optionals if present to new BadgeCards
            .map(badgeType ->
                new BadgeCard(solvedChallenge.getUserId(), badgeType))
            .collect(Collectors.toList());
        badgeRepository.saveAll(newBadgeCards);
        return newBadgeCards;
    }
}
