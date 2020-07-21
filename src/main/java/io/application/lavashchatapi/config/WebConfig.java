package io.application.lavashchatapi.config;

import io.application.lavashchatapi.handlers.ws.WSMessagesHandler;
import io.application.lavashchatapi.handlers.ws.WSPresenceHandler;
import io.application.lavashchatapi.handlers.ws.WSTypingHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.reactive.CorsWebFilter;
import org.springframework.web.cors.reactive.UrlBasedCorsConfigurationSource;
import org.springframework.web.reactive.HandlerMapping;
import org.springframework.web.reactive.handler.SimpleUrlHandlerMapping;
import org.springframework.web.reactive.socket.server.support.WebSocketHandlerAdapter;

import static java.util.Map.of;

@Configuration
public class WebConfig {

    @Bean
    public HandlerMapping handlerMapping(final WSPresenceHandler wsPresenceHandler,
                                         final WSMessagesHandler wsMessagesHandler,
                                         final WSTypingHandler wsTypingHandler) {
        return new SimpleUrlHandlerMapping(of("/presence", wsPresenceHandler,
                "/messages", wsMessagesHandler,
                "/typing", wsTypingHandler), -1);
    }

    @Bean
    public WebSocketHandlerAdapter webSocketHandlerAdapter() {
        return new WebSocketHandlerAdapter();
    }

    @Bean
    public CorsWebFilter corsWebFilter() {
        final var config = new CorsConfiguration();
        config.addAllowedOrigin("*");
        config.addAllowedHeader("*");
        config.addAllowedMethod("*");

        final var source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);

        return new CorsWebFilter(source);
    }
}