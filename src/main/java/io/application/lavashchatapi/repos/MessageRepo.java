package io.application.lavashchatapi.repos;

import io.application.lavashchatapi.entities.Message;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Flux;

public interface MessageRepo extends ReactiveMongoRepository<Message, String> {

    @Query(value = "{}",sort = "{timestamp : -1}")
    Flux<Message> findAllByTimestamp();
}
