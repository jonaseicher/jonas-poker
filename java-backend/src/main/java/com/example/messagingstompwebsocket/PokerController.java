package com.example.messagingstompwebsocket;

import java.security.Principal;

import com.example.messagingstompwebsocket.pokerlib.TexasHoldemGame;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageExceptionHandler;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.annotation.SendToUser;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PokerController {

	@Autowired
	SimpMessagingTemplate template;

	@Autowired
	PokerService pokerService;

	@GetMapping("/rest")
	public String rest() {
		return "Welcome Player! Use the Frontend to connect to the Websockets!";
	}
	

	//////////////// Join a poker table

	@MessageMapping("/join/table")
	@SendTo
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
