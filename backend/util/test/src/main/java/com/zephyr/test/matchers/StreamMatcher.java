package com.zephyr.test.matchers;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import org.hamcrest.Matcher;
import org.springframework.cloud.stream.test.matcher.MessageQueueMatcher;
import org.springframework.messaging.Message;

import java.util.concurrent.TimeUnit;

public final class StreamMatcher {

    private StreamMatcher() {

    }

    public static <T> PayloadMatcher<T> payload(ObjectMapper objectMapper, Class<T> clazz) {
        return new PayloadMatcher<>(objectMapper, clazz);
    }

    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    public static class PayloadMatcher<T> {

        private final ObjectMapper objectMapper;
        private final Class<T> clazz;

        public MessageQueueMatcher<T> matches(Matcher<T> payloadMatcher) {
            return new MessageQueueMatcher<>(payloadMatcher, 5, TimeUnit.SECONDS,
                    new MessageQueueMatcher.Extractor<>("a message whose payload ") {

                        @Override
                        @SneakyThrows
                        public T apply(Message<?> m) {
                            return objectMapper.readValue((String) m.getPayload(), clazz);
                        }
                    });
        }
    }
}
