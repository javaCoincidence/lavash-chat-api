package io.application.lavashchatapi;

import io.application.lavashchatapi.dtos.SessionDTO;
import io.application.lavashchatapi.dtos.TypingDTO;
import io.application.lavashchatapi.entities.Message;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import reactor.core.publisher.DirectProcessor;
import reactor.core.publisher.ReplayProcessor;

@SpringBootApplication
public class LavashChatApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(LavashChatApiApplication.class, args);
    }

    @Bean
    public ReplayProcessor<SessionDTO> presenceProcessor() {
        return ReplayProcessor.create(100);
    }

    @Bean
    public DirectProcessor<TypingDTO> typingProcessor() {
        return DirectProcessor.create();
    }

    @Bean
    public ReplayProcessor<Message> messageProcessor() {
        return ReplayProcessor.create(100);
    }
}