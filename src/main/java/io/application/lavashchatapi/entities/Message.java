package io.application.lavashchatapi.entities;

import lombok.*;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.Instant;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "messages")
public class Message {
    private String text;
    private String from;
    private long timestamp = Instant.now().toEpochMilli();
}