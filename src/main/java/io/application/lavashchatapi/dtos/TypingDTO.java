package io.application.lavashchatapi.dtos;

import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class TypingDTO {
    private String id;
    private String username;
    private boolean typing;
}
