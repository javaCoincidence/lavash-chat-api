package io.application.lavashchatapi.services;

import io.application.lavashchatapi.apis.MessageService;
import io.application.lavashchatapi.entities.Message;
import io.application.lavashchatapi.repos.MessageRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

@Service
@RequiredArgsConstructor
class MessageServiceImpl implements MessageService {

    private final MessageRepo repo;

    @Override
    public Flux<Message> get() {
        return repo.findAllByTimestamp()
                .take(100);
    }
}