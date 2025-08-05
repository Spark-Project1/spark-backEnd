package com.spark.chat.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {


    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint("/chat")// socket 연결 url
            .setAllowedOrigins("*") // 모든 출처에서의 연결 허용
            .withSockJS(); // SockJS를 사용하여 WebSocket 연결을 지원


    }

    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        // 서버에서 클라이언트로 메시지를 전달시 해당 prefix를 사용
        registry.enableSimpleBroker("/sub");

        // 클라이언트가 보낸 메시지가 /app 시작하는 주제에 대한 메시지를 브로커가 처리하도록 설정
        registry.setApplicationDestinationPrefixes("/pub");
    }

}
