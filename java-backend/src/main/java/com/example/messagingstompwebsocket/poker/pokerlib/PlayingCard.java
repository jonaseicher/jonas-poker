package com.example.messagingstompwebsocket.poker.pokerlib;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * Represents a single playing card.
 *
 * @author andyehou@gmail.com (Andy Hou)
 */
@Data
public class PlayingCard {
  private final int rank;
  private final Suit suit;

  public enum Suit {
    DIAMOND, CLUB, HEART, SPADE,
  }

  /**
   * @param index The index between 0 and 51 inclusive.
   * @return a new card from the given index.
   */
  public static PlayingCard fromIndex(int index) {
    return new PlayingCard(index % 13, Suit.values()[index / 13]);
  }

  /**
   * @return the index corresponding to this card.
   */
  public int toIndex() {
    return suit.ordinal() * 13 + rank;
  }

}
