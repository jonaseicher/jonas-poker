package com.example.messagingstompwebsocket;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageExceptionHandler;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.annotation.SendToUser;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.HtmlUtils;

@RestController
public class GreetingController {

	@Autowired
	SimpMessagingTemplate template;

	// @GetMapping("/rest")
	// public String rest() {
	// 	return "Welcome!";
	// }
	
	// calls to /app/hello will be routed to this controller, all calls without app will be handled by a simple broker (receive/send)
	// @MessageMapping("/hello")
	// @SendTo("/topic/greetings")
	// public Greeting greeting(String message) throws Exception {
	// 	System.out.println("Received message " + message + " in /app/hello. sending to /topic/greetings");
	// 	return new Greeting("Hello, " + HtmlUtils.htmlEscape(message) + "!");
	// }

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

}
