package com.example.messagingstompwebsocket.kingdom.service;

import com.example.messagingstompwebsocket.kingdom.model.Card;
import com.example.messagingstompwebsocket.kingdom.model.Deck;

import org.springframework.stereotype.Service;

@Service
public class DeckService {

   public void addCards(Deck deck, Class<Card> clazz, int amount) {
    try {
      for (int i = 0; i < amount; i++) {
        deck.getDraw().add(clazz.newInstance());
      }
    } catch (IllegalAccessException | InstantiationException e) {
      e.printStackTrace();
    }
  }

  /**
   * Randomly shuffles the draw pile.
   */
  public void shuffle(Deck<Card> deck) {
    for (int i = 0; i < deck.getDraw().size() - 1; i++) {
      // Swap index i with a random card index>=i.
      int swapIndex = i + (int) (Math.random() * (deck.getDraw().size() - i));
      Card tmp = deck.getDraw().get(i);
      deck.getDraw().set(i, deck.getDraw().get(swapIndex));
      deck.getDraw().set(swapIndex, tmp);
    }
  }

  /**
   * Draws a card from the top of the Draw.
   *
   * @return the card that was drawn or null if the deck.getDraw is empty.
   */
  public Card drawCard(Deck<Card> deck) {
    if (deck.getDraw().size() == 0) {
      return null;
    }
    return deck.getDraw().remove(0);
  }

}