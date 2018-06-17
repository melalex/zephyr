package com.zephyr.rating.domain;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class Request {

    private QueryCriteria query;
    private LocalDateTime timestamp;
    private String provider;
}
