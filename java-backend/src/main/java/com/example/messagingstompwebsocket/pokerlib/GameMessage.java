package com.example.messagingstompwebsocket.pokerlib;

import lombok.Data;

/**
 * Represents a message sent by a player describing an action or game state.
 */
@Data
 public class GameMessage {
  // The UID of the player sending the message.
  private final Long playerId;
  
  // The type of game message.
  private final Type type;
  
  // The possible types of game messages.
  public enum Type {
    SYNC_GAME_STATE,
    PLAYER_ACTION,
    PLAYER_JOINING,
    PLAYER_LEAVING,
    NEW_HAND,
    REQUEST_NEW_HAND
  }
  
  // The type of action, if the message type is PLAYER_ACTION.
  private final ActionType actionType;
  
  // The possible types of player game actions.
  public enum ActionType {
    NONE,
    FOLD,
    BET
  }
  
  // The number corresponding to:
  //   player bet action        (Type == PLAYER_ACTION)
  //   isHost                   (Type == PLAYER_JOINING)
  //   number of players        (Type == NEW_HAND)
  private final int number;
  
  // The string corresponding to:
  //   player nickname          (Type == PLAYER_JOINING)
  //   new hand game sync data  (Type == NEW_HAND)
  private final String data;
  
  
  public GameMessage(Long playerId, Type type, ActionType actionType, int number, String data) {
    this.playerId = playerId;
    this.type = type;
    this.actionType = actionType;
    this.number = number;
    this.data = data;
  }
  
}
