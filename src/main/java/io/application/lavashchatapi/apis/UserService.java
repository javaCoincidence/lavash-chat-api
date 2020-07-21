package io.application.lavashchatapi.apis;

import reactor.core.publisher.Mono;

public interface UserService {

    Mono<String> add(final String username);
}
