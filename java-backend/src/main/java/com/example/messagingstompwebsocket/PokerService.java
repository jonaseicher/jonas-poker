package com.example.messagingstompwebsocket;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import com.example.messagingstompwebsocket.pokerlib.Player;
import com.example.messagingstompwebsocket.pokerlib.TexasHoldemGame;

import org.springframework.stereotype.Service;

@Service
public class PokerService {

  List<TexasHoldemGame> games = new ArrayList<>();

	public TexasHoldemGame joinTable(String tableName, String playerName) {
    for (TexasHoldemGame game: this.games) {
      if (game.getTableName().equals(tableName)) {
        game.getPlayers().add(new Player(UUID.randomUUID(), playerName, 1000));
        return game;
      }
    }
    TexasHoldemGame newGame = new TexasHoldemGame();
    newGame.setTableName(tableName);
    newGame.getPlayers().add(new Player(UUID.randomUUID(), playerName, 1000));
    games.add(newGame);
    return newGame;
  }
  
}
