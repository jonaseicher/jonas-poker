package com.example.messagingstompwebsocket.pokerlib;

import com.example.messagingstompwebsocket.PokerService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.bind.annotation.RestController;

import lombok.Data;
import lombok.extern.java.Log;

@RestController
@Log
public class SimplePokerWsController {

  TexasHoldemGame game;

  @Autowired
  PokerService pokerService;

  @Autowired
	SimpMessagingTemplate messagingTemplate;

  public SimplePokerWsController() {
    game = new TexasHoldemGame();
  }

	@MessageMapping("/poker/game")
	public void getGame(@Payload String tableName) {
    sendTable(tableName);
  }
  
  @MessageMapping("/poker/game/join")
	public void joinGame(@Payload String tableName, JwtAuthenticationToken token) {
    TexasHoldemGame table = pokerService.getTable(tableName);
    String playerId = token.getToken().getClaim("name");
    table.joinTable(playerId);
    sendTable(table);
  }
  
  @MessageMapping("/poker/game/leave")
  public void leaveGame(@Payload String tableName, JwtAuthenticationToken token) {
    TexasHoldemGame table = pokerService.getTable(tableName);
    String playerId = token.getToken().getClaim("name");
    table.leaveTable(playerId);
    sendTable(table);
  }
  
  @MessageMapping("/poker/game/reset")
  public void reset(@Payload String tableName) {
    TexasHoldemGame table = pokerService.getTable(tableName);
    pokerService.getTables().remove(table);
    table = new TexasHoldemGame();
    table.setTableName(tableName);
    pokerService.getTables().add(table);
		sendTable(table);
  }
  
	@MessageMapping("/poker/game/start")
	public void startGame(@Payload String tableName) {
    TexasHoldemGame table = pokerService.getTable(tableName);
    table.dealNewHand();
		sendTable(table);
  }
  
	@MessageMapping("/poker/game/bet")
	public void bet(@Payload Bet bet, JwtAuthenticationToken token) {
    TexasHoldemGame table = pokerService.getTable(bet.tableName);
    String playerId = token.getToken().getClaim("name");
    table.bet(playerId, bet.amount);
		sendTable(table);
  }
  
	@MessageMapping("/poker/game/fold")
	@SendTo("/queue/pokertable")
	public void fold(@Payload String tableName, JwtAuthenticationToken token) {
    TexasHoldemGame table = pokerService.getTable(tableName);
    String playerId = token.getToken().getClaim("name");
    table.fold(playerId);
		sendTable(table);
  }
  
	@MessageMapping("/poker/game/newhand")
	public void newHand(@Payload String tableName) {
    TexasHoldemGame table = pokerService.getTable(tableName);
    table.dealNewHand();
    sendTable(table);
  }

  private void sendTable(String tableName) {
    TexasHoldemGame table = pokerService.getTable(tableName);
    sendTable(table);
  }

  /** Send Table object to /queue/pokertable/${tableName} */
  private void sendTable(TexasHoldemGame table) {
    messagingTemplate.convertAndSend("/queue/pokertable/" + table.getTableName(), table);
  }
}

@Data
class Bet {
  String tableName;
  int amount;
}