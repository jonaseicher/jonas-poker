package com.example.messagingstompwebsocket;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.annotation.SendToUser;
import org.springframework.stereotype.Controller;
import org.springframework.web.util.HtmlUtils;

@Controller
public class GreetingController {

	// calls to /app/hello will be routed to this controller, all calls without app will be handled by a simple broker (receive/send)
	@MessageMapping("/hello")
	@SendTo("/topic/greetings")
	public Greeting greeting(HelloMessage message) throws Exception {
		System.out.println("Received message " + message.getName() + " in /app/hello. sending to /topic/greetings");
		return new Greeting("Hello, " + HtmlUtils.htmlEscape(message.getName()) + "!");
	}

	@MessageMapping("/cards")
	@SendToUser("/karten")
	public String cards(String message) throws Exception {
		System.out.println("Received message in /app/cards: " + message);
		return "Here are your cards!";
	}

}
