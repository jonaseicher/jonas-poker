package com.example.messagingstompwebsocket;

import java.util.ArrayList;
import java.util.List;

import com.example.messagingstompwebsocket.pokerlib.TexasHoldemGame;

import org.springframework.stereotype.Service;

@Service
public class PokerService {

  List<TexasHoldemGame> games = new ArrayList<>();

  public PokerService() {
    TexasHoldemGame game1 = new TexasHoldemGame();
    game1.setTableName("Beginners Table");
    this.games.add(game1);
    TexasHoldemGame game2 = new TexasHoldemGame();
    game2.setTableName("Table-1337");
    this.games.add(game2);
    TexasHoldemGame game3 = new TexasHoldemGame();
    game3.setTableName("PrOz 0nlY!");
    this.games.add(game3);
    TexasHoldemGame game4 = new TexasHoldemGame();
    game4.setTableName("ALL YOUR BASE");
    this.games.add(game4);
  }

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
