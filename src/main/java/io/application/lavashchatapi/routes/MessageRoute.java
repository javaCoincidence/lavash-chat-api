package io.application.lavashchatapi.routes;

import io.application.lavashchatapi.apis.MessageHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RouterFunctions.route;

@Configuration
public class MessageRoute {

    @Bean
    public RouterFunction<ServerResponse> messageFunction(final MessageHandler handler) {
        return route()
                .path("/messages", builder -> builder
                        .GET("", handler::get))
                .build();
    }
}