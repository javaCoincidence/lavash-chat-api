package io.application.lavashchatapi.common;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.application.lavashchatapi.entities.Message;
import lombok.SneakyThrows;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.codec.json.Jackson2JsonDecoder;
import reactor.core.publisher.Mono;

import static org.springframework.core.ResolvableType.forClass;
import static reactor.core.publisher.Mono.just;

public class JsonUtils {
    private static final Jackson2JsonDecoder decoder = new Jackson2JsonDecoder();
    private static final ObjectMapper mapper = new ObjectMapper();

    public static <T> Mono<Object> decode(final DataBuffer dataBuffer,final Class<T> tClass) {
        return decoder.decodeToMono(just(dataBuffer), forClass(tClass), null, null);
    }

    @SneakyThrows
    public static <T> String encode(final T object) {
        return mapper.writeValueAsString(object);
    }
}
