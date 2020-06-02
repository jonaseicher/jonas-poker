package com.example.messagingstompwebsocket.pokerlib;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.java.Log;

@RestController
@Log
public class SimplePokerWsController {

  TexasHoldemGame game;

  public SimplePokerWsController() {
    game = new TexasHoldemGame();
  }

	@MessageMapping("/poker/game")
	@SendTo("/queue/pokertable")
	public TexasHoldemGame getGame() {
		return game;
  }

  @MessageMapping("/poker/game/join")
  @SendTo("/queue/pokertable")
	public TexasHoldemGame joinGame(@RequestParam String playerId) {
    game.joinTable(playerId);
		return game;
  }

  @MessageMapping("/poker/game/leave")
  @SendTo("/queue/pokertable")
	public TexasHoldemGame leaveGame(@RequestParam String playerId) {
    game.leaveTable(playerId);
		return game;
  }
  
  @MessageMapping("/poker/game/reset")
  @SendTo("/queue/pokertable")
	public TexasHoldemGame reset() {    
    game = new TexasHoldemGame();
		return game;
  }
  
	@MessageMapping("/poker/game/start")
	@SendTo("/queue/pokertable")
	public TexasHoldemGame startGame() {
    game.dealNewHand();
		return game;
  }

	@MessageMapping("/poker/game/bet")
	@SendTo("/queue/pokertable")
	public TexasHoldemGame bet(@RequestParam String playerId, @RequestParam int amount) {
    game.bet(playerId, amount);
		return game;
  }
  
	@MessageMapping("/poker/game/fold")
	@SendTo("/queue/pokertable")
	public TexasHoldemGame fold(@RequestParam String playerId) {
    game.fold(playerId);
		return game;
  }

}