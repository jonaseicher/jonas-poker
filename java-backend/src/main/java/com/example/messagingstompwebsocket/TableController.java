package com.example.messagingstompwebsocket;

import java.util.List;

import com.example.messagingstompwebsocket.pokerlib.TexasHoldemGame;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TableController {

	@Autowired
	PokerService pokerService;

	@MessageMapping("/lobby/tables")
	@SendTo("/lobby/tables")
	public List<TexasHoldemGame> tables(JwtAuthenticationToken token) {
		return pokerService.games;
	}
	
}