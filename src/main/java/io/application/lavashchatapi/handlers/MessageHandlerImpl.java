package io.application.lavashchatapi.handlers;

import io.application.lavashchatapi.apis.MessageHandler;
import io.application.lavashchatapi.apis.MessageService;
import io.application.lavashchatapi.entities.Message;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import static org.springframework.web.reactive.function.server.ServerResponse.ok;

@Component
@RequiredArgsConstructor
class MessageHandlerImpl implements MessageHandler {

    private final MessageService service;

    @Override
    public Mono<ServerResponse> get(ServerRequest serverRequest) {
        return ok().body(service.get(), Message.class);
    }
}
