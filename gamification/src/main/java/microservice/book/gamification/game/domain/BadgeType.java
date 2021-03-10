package microservice.book.gamification.game.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * Enumeration with the different types of Badges that a user can win.
 *
 * @author Alexander Bravo
 */
@RequiredArgsConstructor
@Getter
public enum BadgeType {
  BRONZE("Bronze"),
  SILVER("Silver"),
  GOLD("Gold"),
  // Other badges won for different conditions
  FIRST_WON("First time"),
  LUCKY_NUMBER("Lucky number");

  final String description;
}
