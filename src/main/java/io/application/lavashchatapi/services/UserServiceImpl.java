package io.application.lavashchatapi.services;


import io.application.lavashchatapi.apis.UserService;
import io.application.lavashchatapi.entities.User;
import io.application.lavashchatapi.repos.UserRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
class UserServiceImpl implements UserService {

    private final UserRepo repo;

    @Override
    public Mono<String> add(String username) {
        return repo.save(new User(null, username))
                .map(User::getUsername);
    }
}