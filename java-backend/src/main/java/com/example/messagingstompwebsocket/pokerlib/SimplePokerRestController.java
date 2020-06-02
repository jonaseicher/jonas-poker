package com.example.messagingstompwebsocket.pokerlib;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.java.Log;

@RestController
@Log
public class SimplePokerRestController {

  TexasHoldemGame game;

  public SimplePokerRestController() {
    game = new TexasHoldemGame();
  }

	@GetMapping("/poker/game")
	public TexasHoldemGame getGame() {
		return game;
  }

	@GetMapping("/poker/game/join")
	public TexasHoldemGame joinGame(@RequestParam String playerName) {
    game.joinTable(playerName);
		return game;
  }

  @GetMapping("/poker/game/leave")
	public TexasHoldemGame leaveGame(@RequestParam String playerName) {
    game.leaveTable(playerName);
		return game;
  }
  
  @GetMapping("/poker/game/reset")
	public TexasHoldemGame reset() {    
    game = new TexasHoldemGame();
		return game;
  }

  
	@GetMapping("/poker/game/start")
	public TexasHoldemGame startGame() {
    game.dealNewHand();
		return game;
  }

}
