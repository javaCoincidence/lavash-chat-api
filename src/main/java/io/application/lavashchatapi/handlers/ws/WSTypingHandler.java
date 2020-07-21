package io.application.lavashchatapi.handlers.ws;

import io.application.lavashchatapi.common.JsonUtils;
import io.application.lavashchatapi.dtos.TypingDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.socket.WebSocketHandler;
import org.springframework.web.reactive.socket.WebSocketMessage;
import org.springframework.web.reactive.socket.WebSocketSession;
import reactor.core.publisher.DirectProcessor;
import reactor.core.publisher.Mono;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import static reactor.core.publisher.Mono.zip;

@Component
@RequiredArgsConstructor
public class WSTypingHandler implements WebSocketHandler {

    private final DirectProcessor<TypingDTO> typingProcessor;
    private final ConcurrentMap<String, TypingDTO> typingMap = new ConcurrentHashMap<>();

    @Override
    public Mono<Void> handle(WebSocketSession webSocketSession) {

        final var source = webSocketSession
                .receive()
                .map(WebSocketMessage::retain)
                .map(WebSocketMessage::getPayload)
                .flatMap(dataBuffer -> JsonUtils.decode(dataBuffer, TypingDTO.class))
                .cast(TypingDTO.class)
                .doOnNext(typingDTO -> typingDTO.setId(webSocketSession.getId()))
                .doOnNext(typingDTO -> typingMap.put(typingDTO.getId(), typingDTO))
                .doOnNext(typingProcessor::onNext)
                .doOnComplete(() -> {
                    final var typingDTO = typingMap.get(webSocketSession.getId());
                    typingDTO.setTyping(false);
                    typingProcessor.onNext(typingDTO);
                })
                .then();

        final var output = typingProcessor
                .map(JsonUtils::encode)
                .map(webSocketSession::textMessage);

        return zip(source, webSocketSession.send(output))
                .then();
    }
}