package io.application.lavashchatapi.handlers.ws;

import io.application.lavashchatapi.common.JsonUtils;
import io.application.lavashchatapi.entities.Message;
import io.application.lavashchatapi.repos.MessageRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.socket.WebSocketHandler;
import org.springframework.web.reactive.socket.WebSocketMessage;
import org.springframework.web.reactive.socket.WebSocketSession;
import reactor.core.publisher.Mono;
import reactor.core.publisher.ReplayProcessor;

import static reactor.core.publisher.Mono.zip;

@Component
@RequiredArgsConstructor
public class WSMessagesHandler implements WebSocketHandler {

    private final ReplayProcessor<Message> messageProcessor;
    private final MessageRepo messageRepo;

    @Override
    public Mono<Void> handle(WebSocketSession webSocketSession) {

        final var source = webSocketSession
                .receive()
                .map(WebSocketMessage::retain)
                .map(WebSocketMessage::getPayload)
                .flatMap(dataBuffer -> JsonUtils.decode(dataBuffer, Message.class))
                .cast(Message.class)
                .flatMap(messageRepo::save)
                .doOnNext(messageProcessor::onNext)
                .then();

        final var output = messageProcessor
                .map(JsonUtils::encode)
                .map(webSocketSession::textMessage);

        return zip(source, webSocketSession.send(output))
                .then();
    }
}