package com.zephyr.rating.domain;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Data
@Document
public class Request {

    @Id
    private String id;
    private Query query;
    private LocalDateTime timestamp;
    private String provider;
}
