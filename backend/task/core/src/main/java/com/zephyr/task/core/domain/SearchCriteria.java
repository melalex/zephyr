package com.zephyr.task.core.domain;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Data
@Document
public class SearchCriteria {

    @Id
    private String id;

    @LastModifiedDate
    private LocalDateTime lastHit;
    private LocalDateTime lastUpdate;
    private long hitsCount;
    private String transactionId;

    private String query;
    private Place place;
    private String languageIso;
    private UserAgent userAgent;
}