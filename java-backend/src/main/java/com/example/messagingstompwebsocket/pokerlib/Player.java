package com.example.messagingstompwebsocket.pokerlib;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;

/**
 * Represents a poker player.
 *
 * @author andyehou@gmail.com (Andy Hou)
 */
@Data
public class Player {
  // The id of the player.
  private String id;
  // The name of the player.
  private String name;
  // The number of chips the player has. This does not include any chips that the player has
  // contributed to the pot or has put up in the current round of betting.
  private int chips;
  // The number of chips the player has put up in the current round of betting. This does not
  // include any chips from previous rounds of betting, they get added to the pot as soon as
  // the round of betting ends.
  private int bet;
  // The number of chips the player has put in the pot for the current hand. Does not include
  // the chips in the current round of betting.
  private int potContribution;
  // The number of chips awarded to the player after a hand, including chips that are returned
  // because no other player called a bet.
  private int chipsAwarded;
  // The player's pocket cards.
  private List<Card> pocketCards;
  // The best hand the player can make with the board cards.
  private Hand bestHand;
  // True if this player has folded.
  private boolean isFolded;
  // The next player in the direction of play (clockwise).
  @JsonIgnore
  private Player nextPlayer;
  // True if this player is ready for a new hand.
  private boolean isReady;
  
  private static final String LOG_TAG = Player.class.getSimpleName();
  
  public Player(String id, String name, int chips) {
    this.id = id;
    this.name = name;
    this.chips = chips;
    pocketCards = new ArrayList<Card>();
    cleanupHand();
  }
  
  
  /**
   * @return the next player that is not broke or null if all players are broke.
   */
  @JsonIgnore
  public Player getNextPlayerNotBroke() {
    Player first = this.nextPlayer;
    Player next = this.nextPlayer;
    while (next.isBroke()) {
      next = next.nextPlayer;
      if (next == first) {
        return null;
      }
    }
    return next;
  }
  
  /**
   * Resets member variables for a new hand.
   */
  public void cleanupHand() {
    bet = 0;
    potContribution = 0;
    pocketCards.clear();
    bestHand = null;
    isFolded = false;
    chipsAwarded = 0;
  }
  
  /**
   * Draws 2 pocket cards.
   * @param deck The deck to draw cards from. Must have at least 2 cards.
   */
  public void drawCards(Deck deck) {
    pocketCards.add(deck.drawCard());
    pocketCards.add(deck.drawCard());
  }
  
  
  /**
   * Constructs the best hand from this player's pocket cards and the board cards.
   * @param boardCards The shared community cards on the board.
   */
  public void constructBestHand(List<Card> boardCards) {
    List<Card> cards = new ArrayList<Card>();
    cards.addAll(pocketCards);
    cards.addAll(boardCards);
    bestHand = new HandPool(cards).getBestHand();
  }
  
  /**
   * Sets the player's bet amount for the current round of betting.
   * Must be at least the previous bet amount and no greater than the number of chips.
   * @param bet The number of chips to bet. May be less than the current bet to return chips
   *     to the player's wallet.
   */
  public void setBet(int bet) {
    int betDifference = bet - this.bet;
    chips -= betDifference;
    this.bet = bet;

    if (chips < 0) {
      System.out.println(LOG_TAG + " Player bet by " + bet + ", but only had " +
          (chips + betDifference) + " chips.");
    }
  }
    
  /**
   * Causes this player to fold the hand.
   */
  public void fold() {
    isFolded = true;
  }
  
  /**
   * Resets member variables for a new round of betting.
   */
  public void concludeBettingRound() {
    potContribution += bet;
    bet = 0;
  }
  
  /**
   * Gives this player the given number of chips.
   * @param chipsAwarded The number of chips to give.
   */
  public void awardChips(int chipsAwarded) {
    this.chipsAwarded += chipsAwarded;
    chips += chipsAwarded;
  }
  
  /**
   * @return The total bet the player has made for the current hand.
   */
  public int getTotalBet() {
    return potContribution + bet;
  }
  
  /**
   * @return The maximum bet that the player can make for the current betting round.
   *     Betting this amount would have the player go all-in.
   */
  public int getMaxBet() {
    return chips + bet;
  }
  
  /**
   * @return true if this player is out of the game.
   */
  public boolean isBroke() {
    return chips + bet + potContribution == 0;
  }
  
  /**
   * @return the net value in chips lost or earned for a hand.
   */
  public int getNet() {
    return chipsAwarded - potContribution;
  }
  
}
