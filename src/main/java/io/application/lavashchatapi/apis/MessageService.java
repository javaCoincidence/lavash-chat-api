package io.application.lavashchatapi.apis;

import io.application.lavashchatapi.entities.Message;
import reactor.core.publisher.Flux;

public interface MessageService {

    Flux<Message> get();
}
