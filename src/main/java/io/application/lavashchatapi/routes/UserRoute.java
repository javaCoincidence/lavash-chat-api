package io.application.lavashchatapi.routes;

import io.application.lavashchatapi.apis.UserHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RouterFunctions.route;

@Configuration
public class UserRoute {

    @Bean
    public RouterFunction<ServerResponse> userFunction(final UserHandler handler) {
        return route()
                .path("/user", builder -> builder
                        .POST("/login", handler::add))
                .build();
    }
}