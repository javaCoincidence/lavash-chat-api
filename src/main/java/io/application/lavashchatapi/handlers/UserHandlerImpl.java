package io.application.lavashchatapi.handlers;

import io.application.lavashchatapi.apis.UserHandler;
import io.application.lavashchatapi.apis.UserService;
import io.application.lavashchatapi.dtos.LoginDTO;
import io.application.lavashchatapi.dtos.ResponseMessage;
import io.application.lavashchatapi.repos.UserRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import static org.springframework.util.StringUtils.isEmpty;
import static org.springframework.web.reactive.function.server.ServerResponse.badRequest;
import static org.springframework.web.reactive.function.server.ServerResponse.ok;

@Component
@RequiredArgsConstructor
class UserHandlerImpl implements UserHandler {

    private final UserService service;
    private final UserRepo repo;

    @Override
    public Mono<ServerResponse> add(ServerRequest serverRequest) {
        return serverRequest.bodyToMono(LoginDTO.class)
                .filter(loginDTO -> !isEmpty(loginDTO.getUsername()))
                .map(LoginDTO::getUsername)
                .filterWhen(username -> repo.existsByUsername(username)
                        .map(aBoolean -> !aBoolean))
                .flatMap(service::add)
                .map(ResponseMessage::new)
                .flatMap(responseMessage -> ok().bodyValue(responseMessage))
                .switchIfEmpty(badRequest().build());
    }
}