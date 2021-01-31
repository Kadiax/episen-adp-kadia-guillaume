package com.episen.websockethub.config;

import com.episen.websockethub.handler.ImageWebSocketHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

@Configuration
@EnableWebSocket
public class WebSocketConfiguration implements WebSocketConfigurer {

    private final static String IMAGE_ENDPOINT = "/image_status/*";

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry webSocketHandlerRegistry) {
        webSocketHandlerRegistry.addHandler(getImageWebSocketHandler(), IMAGE_ENDPOINT)
                .setAllowedOrigins("*");

    }

    @Bean
    public WebSocketHandler getImageWebSocketHandler(){
        return new ImageWebSocketHandler();
    }
}

