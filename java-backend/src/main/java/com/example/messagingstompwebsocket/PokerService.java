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
        return game;
      }
    }
    TexasHoldemGame newGame = new TexasHoldemGame();
    newGame.setTableName(tableName);
    games.add(newGame);
    return newGame;
  }
  
}
