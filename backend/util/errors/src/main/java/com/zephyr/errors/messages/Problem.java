package com.zephyr.errors.messages;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
public class Problem {

    private LocalDateTime timestamp;
    private String type;
    private int status;
    private String detail;
    private List<NestedError> errors;

    @Data
    @Builder
    public static class NestedError {

        private String field;
        private Object rejected;
        private String message;
    }
}
