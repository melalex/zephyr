package com.zephyr.errors.messages;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
public class Problem {

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy hh:mm:ss")
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
