package com.example.messagingstompwebsocket;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import com.example.messagingstompwebsocket.pokerlib.Player;
import com.example.messagingstompwebsocket.pokerlib.TexasHoldemGame;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageExceptionHandler;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.annotation.SendToUser;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.Data;

@RestController
public class PokerController {

	@Autowired
	SimpMessagingTemplate template;

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
		System.out.println("Received message in /app/karten: " + message + " from " + user);
		return "Here are your karten! ";
	}

	@MessageExceptionHandler
	@SendToUser("/queue/errors")
	public String handleException(Throwable exception) {
			return exception.getMessage();
	}



















	// @MessageMapping("/humba")
	// public void karten2(String message, Principal user) throws Exception {
	// 	System.out.println("Received message in /app/humba: " + message + " from " + user.getName());
	// 	template.convertAndSendToUser(user.getName(), "/queue/humba", message);
	// }

	// calls to /app/hello will be routed to this controller, all calls without app will be handled by a simple broker (receive/send)
	// @MessageMapping("/hello")
	// @SendTo("/topic/greetings")
	// public Greeting greeting(String message) throws Exception {
	// 	System.out.println("Received message " + message + " in /app/hello. sending to /topic/greetings");
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

	Long id;

	@JsonIgnore
	Player player;

	public void initPlayer() {
		if (this.player == null) {
			this.player = new Player(id, name, 500);
		}
	}

}
