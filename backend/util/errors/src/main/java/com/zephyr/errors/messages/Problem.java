package com.zephyr.errors.messages;

import lombok.Data;

import java.util.List;

@Data
public class Problem {
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
