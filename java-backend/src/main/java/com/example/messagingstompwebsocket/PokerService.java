package com.example.messagingstompwebsocket;

import java.util.ArrayList;
import java.util.List;

import com.example.messagingstompwebsocket.pokerlib.TexasHoldemGame;

import org.springframework.stereotype.Service;

@Service
public class PokerService {

  List<TexasHoldemGame> games = new ArrayList<>();

	public TexasHoldemGame joinTable(String tableName, String playerName) {
    for (TexasHoldemGame game: this.games) {
      if (game.getTableName().equals(tableName)) {
        // TODO check and add player
        return game;
      }
    }
    TexasHoldemGame newGame = new TexasHoldemGame();
    newGame.setTableName(tableName);
    games.add(newGame);
    // newGame.getPlayers().add(e) add player
    return newGame;
  }
  
}
