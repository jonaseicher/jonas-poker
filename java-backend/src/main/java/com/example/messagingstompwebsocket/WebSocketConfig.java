package com.example.messagingstompwebsocket;

import java.security.Principal;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;
import org.springframework.web.socket.server.HandshakeInterceptor;
import org.springframework.web.socket.server.support.DefaultHandshakeHandler;

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

	@Override
	public void configureMessageBroker(MessageBrokerRegistry config) {
		config.setApplicationDestinationPrefixes("/app");
		config.setUserDestinationPrefix("/user/"); // default is "/user/", so this should have no effect
	}

	@Override
	public void registerStompEndpoints(StompEndpointRegistry registry) {
		// registry.addEndpoint("/gs-guide-websocket").withSockJS();
		// registry.addEndpoint("/ws").setAllowedOrigins("http://localhost:8180").withSockJS();
		// registry.addEndpoint("/ws1").setAllowedOrigins("http://localhost:8180");
		registry.addEndpoint("/stomp")
		.setAllowedOrigins("*")
		.setHandshakeHandler(new MyHandshakeHandler())
		.addInterceptors(new SessionIdHandshakeHandler());		
	}

}

class MyHandshakeHandler extends DefaultHandshakeHandler {

	@Override
	protected Principal determineUser(ServerHttpRequest request, WebSocketHandler wsHandler,
			Map<String, Object> attributes) {
		System.out.println("Headers: " + request.getHeaders());
		System.out.println("Principal: " + request.getPrincipal());
		String randomUserName = "jonas"; // "User-" + String.valueOf(Math.random()).substring(3, 7);
		return new Principal() {

			@Override
			public String getName() {
				return randomUserName;
			}
		};
	}
}

class SessionIdHandshakeHandler implements HandshakeInterceptor {

	@Override
	public boolean beforeHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler,
			Map<String, Object> attributes) throws Exception {

		if (request instanceof ServletServerHttpRequest) {
			ServletServerHttpRequest servletRequest = (ServletServerHttpRequest) request;
			HttpSession session = servletRequest.getServletRequest().getSession();
			attributes.put("sessionId", session.getId());
		}
		return true;
	}

	@Override
	public void afterHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler,
			Exception exception) {
		// TODO Auto-generated method stub

	}

	
}
