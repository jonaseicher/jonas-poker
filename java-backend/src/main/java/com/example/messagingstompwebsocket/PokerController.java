package com.example.messagingstompwebsocket;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

import com.example.messagingstompwebsocket.pokerlib.Player;
import com.example.messagingstompwebsocket.pokerlib.TexasHoldemGame;
import com.fasterxml.jackson.annotation.JsonIgnore;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.handler.annotation.MessageExceptionHandler;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.annotation.SendToUser;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

import lombok.Data;
import lombok.extern.java.Log;

@RestController
@Log
public class PokerController {

	@Autowired
	SimpMessagingTemplate messagingTemplate;

	@Autowired
	PokerService pokerService;

	List<VuePlayer> playersInLobby = new ArrayList<>();

	@GetMapping("/rest")
	public String rest() {
		return "Welcome Player! Use the Frontend to connect to the Websockets!";
	}

	//////////////// Lobby

	@MessageMapping("/lobby/player/new")
	@SendTo("/lobby/players")
	public List<VuePlayer> newPlayer(@Payload VuePlayer player) {
		player.initPlayer();
		playersInLobby.add(player);
		return playersInLobby;
	}

	@MessageMapping("/lobby/player/deleted")
	@SendTo("/lobby/players")
	public List<VuePlayer> deletedPlayer(@Payload VuePlayer player) {
		playersInLobby.remove(player);
		return playersInLobby;
	}

	@MessageMapping("/lobby/player/changed")
	@SendTo("/lobby/players")
	public List<VuePlayer> changedPlayer(@Payload VuePlayer player) {
		playersInLobby.forEach(p -> {
			if (p.getId().equals(player.getId())) {
				p.setName(player.getName());
			}
		});
		return playersInLobby;
	}

	//////////////// Chat
	// @MessageMapping("/chat")
	// @SendTo("/chat")
	// public ChatMessage chatMessage(@Payload ChatMessage chatMessage) {
	// 	return chatMessage;
	// }

	//////////////// Join a poker table

	@MessageMapping("/join/table")
	@SendTo("/join/table")
	public TexasHoldemGame joinTable(@Payload String tableName, Principal user) throws Exception {
		String playerName = "Player-" + String.valueOf(Math.random()).substring(3,8);
		if (user != null && user.getName() != null) {
			playerName = user.getName();
		}
		return pokerService.joinTable(tableName, playerName);		
	}



	//////////////// Deal cards to each player.
	
	@MessageMapping("/karten")
	@SendToUser("/queue/karten")
	public String karten(@Payload String message, Principal user) throws Exception {
		log.info("Received message in /app/karten: " + message + " from " + user);
		return "Here are your karten! ";
	}

	// @MessageMapping("/cards")
	// // @SendToUser("/queue/cards")
	// public void cards(@Payload String message, Principal user, SimpMessageHeaderAccessor sha) throws Exception {
	// 	String sessionId = String.valueOf(sha.getHeader("simpSessionId"));
	// 	log.info("Received message in /app/cards: " + message + " from " + user);
	// 	messagingTemplate.convertAndSendToUser("jonas", "/queue/cards", message);	
	// 	// return "Here are your cards!";
	// }

	@MessageExceptionHandler
	@SendToUser("/queue/errors")
	public String handleException(Throwable exception) {
			return exception.getMessage();
	}

	@EventListener
  public void onDisconnectEvent(SessionDisconnectEvent event) {
		log.info("Annotation: Client with username " + event.getUser() + " disconnected");
		log.info("name " + event.getUser().getName() + " disconnected");
		Object o = event.getSessionId();
  }



















	// @MessageMapping("/humba")
	// public void karten2(String message, Principal user) throws Exception {
	// 	log.info("Received message in /app/humba: " + message + " from " + user.getName());
	// 	messagingTemplate.convertAndSendToUser(user.getName(), "/queue/humba", message);
	// }

	// calls to /app/hello will be routed to this controller, all calls without app will be handled by a simple broker (receive/send)
	// @MessageMapping("/hello")
	// @SendTo("/topic/greetings")
	// public Greeting greeting(String message) throws Exception {
	// 	log.info("Received message " + message + " in /app/hello. sending to /topic/greetings");
	// 	return new Greeting("Hello, " + HtmlUtils.htmlEscape(message) + "!");
	// }

}


@Data
class NameChange {
	String oldName;
	String newName;
}

@Data
class VuePlayer {
	String name;

	String id;

	@JsonIgnore
	Player player;

	public void initPlayer() {
		if (this.player == null) {
			this.player = new Player(id, name, 500);
		}
	}

}


@Data
class ChatMessage {
	String user;
	String text;
}