package io.application.lavashchatapi.apis;

import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

public interface MessageHandler {

    Mono<ServerResponse> get(final ServerRequest serverRequest);
}
