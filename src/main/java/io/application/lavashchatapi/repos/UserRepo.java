package io.application.lavashchatapi.repos;

import io.application.lavashchatapi.entities.User;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Mono;

public interface UserRepo extends ReactiveMongoRepository<User, String> {

    Mono<Boolean> existsByUsername(final String username);
}
