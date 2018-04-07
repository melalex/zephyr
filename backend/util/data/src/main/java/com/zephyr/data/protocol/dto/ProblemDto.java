package com.zephyr.data.protocol.dto;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class ProblemDto {

    private LocalDateTime timestamp;
    private String type;
    private int status;
    private String detail;
    private List<NestedError> errors;

    @Data
    public static class NestedError {

        private String field;
        private Object rejected;
        private String message;
    }
}
