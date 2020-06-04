package com.example.messagingstompwebsocket;

import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.messaging.support.MessageHeaderAccessor;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtException;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Component;

@Log
@Order(Ordered.HIGHEST_PRECEDENCE + 99)
@Component
public class BearerTokenInterceptor implements ChannelInterceptor {

    @Autowired
    private JwtDecoder jwtDecoder;

    @Override
    public Message<?> preSend(Message<?> message, MessageChannel channel) {
        StompHeaderAccessor accessor =
                MessageHeaderAccessor.getAccessor(message, StompHeaderAccessor.class);
        // if (true) return message; // TODO: remove! Only use this when the Idp is unreachable
        if (StompCommand.CONNECT.equals(accessor.getCommand())) {

            String bearer = accessor.getNativeHeader("Authorization").stream()
                    .findFirst()
                    .map(s -> s.startsWith("Bearer ") ? s.substring(7): s)
                    .orElse(null);
            System.out.println("WebSocket1337: Bearer " + bearer);
            try {
                Jwt accessToken = jwtDecoder.decode(bearer);
                JwtAuthenticationToken user = new JwtAuthenticationToken(accessToken);
                System.out.println("user.getName: " + user.getName());
                accessor.setUser(user);
                System.out.println("message headers: " + message.getHeaders());
            } catch (JwtException e) {
                log.severe(e.getMessage());
            }
        }

        return message;
    }
}
