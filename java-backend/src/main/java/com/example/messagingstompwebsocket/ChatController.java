package com.example.messagingstompwebsocket;

import java.util.ArrayList;
import java.util.List;

import com.example.messagingstompwebsocket.pokerlib.Player;
import com.fasterxml.jackson.annotation.JsonIgnore;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.extern.java.Log;

@RestController
@Log
public class ChatController {

	@Autowired
	SimpMessagingTemplate messagingTemplate;

	List<VuePlayer> playersInLobby = new ArrayList<>();

	//////////////// Lobby

	@MessageMapping("/lobby/player/new")
	@SendTo("/lobby/players")
	public List<VuePlayer> newPlayer(JwtAuthenticationToken token) {
		VuePlayer newPlayer = new VuePlayer(token);
		if (!playersInLobby.contains(newPlayer)) {
			playersInLobby.add(newPlayer);
		}
		return playersInLobby;
	}
	
	// @MessageMapping("/lobby/player/deleted")
	// @SendTo("/lobby/players")
	// public List<VuePlayer> deletedPlayer(JwtAuthenticationToken token) {
	// 	VuePlayer player = new VuePlayer(token);
	// 	playersInLobby.remove(player);
	// 	return playersInLobby;
	// }

	// @MessageMapping("/lobby/player/changed")
	// @SendTo("/lobby/players")
	// public List<VuePlayer> changedPlayer(@Payload VuePlayer player) {
	// 	player.getId();
	// 	return playersInLobby;
	// }

	@EventListener
  public void onDisconnectEvent(SessionDisconnectEvent event) {
		log.info("Client with Name " + event.getUser().getName() + " disconnected");
		playersInLobby.remove(new VuePlayer((JwtAuthenticationToken)event.getUser()));
		messagingTemplate.convertAndSend("/lobby/players", playersInLobby);
  }

}

@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
class VuePlayer {
	
	public String getName() {
		return player.getName();
	}

	@EqualsAndHashCode.Include
	public String getId() {
		return player.getId();
	
	}
	public void setName(String value) {
		player.setName(value) ;
	}

	public void setId(String value) {
		player.setId(value) ;
	}

	@JsonIgnore
	Player player;
	
	@JsonIgnore
	JwtAuthenticationToken token;

	VuePlayer(JwtAuthenticationToken token) {
		this.token = token;
		if (this.player == null) {
			this.player = new Player(token.getName(), token.getToken().getClaimAsString("name"), 500);
		}
	}
}

@Data
class ChatMessage {
	String user;
	String text;
}
