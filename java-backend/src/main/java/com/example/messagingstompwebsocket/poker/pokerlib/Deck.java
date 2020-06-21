package com.example.messagingstompwebsocket.poker.pokerlib;

import java.util.ArrayList;
import java.util.List;

public class Deck {
  private List<PlayingCard> deck;

  private static final String LOG_TAG = Deck.class.getSimpleName();

  /**
   * Constructs a new deck with the cards in order.
   */
  public Deck() {
    deck = new ArrayList<PlayingCard>();
    for (int i = 0; i < 52; i++) {
      deck.add(PlayingCard.fromIndex(i));
    }
  }

  /**
   * Randomly shuffles the deck.
   */
  public void shuffle() {
    for (int i = 0; i < deck.size() - 1; i++) {
      // Swap index i with a random card index>=i.
      int swapIndex = i + (int)(Math.random() * (deck.size() - i));
      PlayingCard tmp = deck.get(i);
      deck.set(i, deck.get(swapIndex));
      deck.set(swapIndex, tmp);
    }
  }

  /**
   * Stacks the deck so that the given cards are drawn. Used for debugging.
   * @param indexes The indexes of the cards to draw.
   */
  public void stack(int indexes[]) {
    deck.clear();
    for (int i = 0; i < indexes.length; i++) {
      deck.add(PlayingCard.fromIndex(indexes[i]));
    }
  }

  /**
   * Draws a card from the top of the deck.
   * @return the card that was drawn or null if the deck is empty.
   */
  public PlayingCard drawCard() {
    if (deck.size() == 0) {
      return null;
    }
    return deck.remove(0);
  }

  /**
   * Serializes this Deck into a string.
   * @return a string representing this Deck.
   */
  public String serialize() {
    String s = "";
    for (int i = 0; i < deck.size(); i++) {
      s += deck.get(i).toIndex();
      if (i < deck.size() - 1) {
        s += ".";
      }
    }
    return s;
  }

  /**
   * Deserializes a string to create a new Deck.
   * @param s The string to deserialize.
   * @return a new Deck.
   */
  public static Deck deserialize(String s) {
    Deck deck = new Deck();
    String indexStrings[] = s.split("\\.");
    int indexes[] = new int[indexStrings.length];
    for (int i = 0; i < indexStrings.length; i++) {
      indexes[i] = Integer.parseInt(indexStrings[i]);
    }
    deck.stack(indexes);
    return deck;
  }
}