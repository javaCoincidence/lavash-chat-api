package io.application.lavashchatapi.handlers.ws;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.application.lavashchatapi.common.JsonUtils;
import io.application.lavashchatapi.dtos.LoginDTO;
import io.application.lavashchatapi.dtos.SessionDTO;
import io.application.lavashchatapi.entities.Message;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.http.codec.json.Jackson2JsonDecoder;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.socket.WebSocketHandler;
import org.springframework.web.reactive.socket.WebSocketMessage;
import org.springframework.web.reactive.socket.WebSocketSession;
import reactor.core.publisher.Mono;
import reactor.core.publisher.ReplayProcessor;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import static org.springframework.core.ResolvableType.forClass;
import static reactor.core.publisher.Mono.just;
import static reactor.core.publisher.Mono.zip;

@Component
@RequiredArgsConstructor
public class WSPresenceHandler implements WebSocketHandler {

    private final ReplayProcessor<SessionDTO> presenceProcessor;
    private final ConcurrentMap<String, SessionDTO> sessionMap = new ConcurrentHashMap<>();

    @Override
    public Mono<Void> handle(WebSocketSession webSocketSession) {

        final var source = webSocketSession
                .receive()
                .map(WebSocketMessage::retain)
                .map(WebSocketMessage::getPayload)
                .flatMap(dataBuffer -> JsonUtils.decode(dataBuffer, LoginDTO.class))
                .cast(LoginDTO.class)
                .map(LoginDTO::getUsername)
                .map(username -> new SessionDTO(webSocketSession.getId(), username, true))
                .doOnNext(sessionDTO -> sessionMap.put(sessionDTO.getId(), sessionDTO))
                .doOnNext(presenceProcessor::onNext)
                .doOnComplete(() -> {
                    final var sessionDTO = sessionMap.get(webSocketSession.getId());
                    sessionDTO.setPresent(false);
                    presenceProcessor.onNext(sessionDTO);
                })
                .then();

        final var output = presenceProcessor
                .map(JsonUtils::encode)
                .map(webSocketSession::textMessage);

        return zip(source, webSocketSession.send(output))
                .then();
    }

}