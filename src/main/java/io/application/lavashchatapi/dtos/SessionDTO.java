package io.application.lavashchatapi.dtos;

import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class SessionDTO {
    private String id;
    private String username;
    private boolean present;
}
