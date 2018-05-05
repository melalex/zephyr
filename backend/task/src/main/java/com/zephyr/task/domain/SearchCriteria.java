package com.zephyr.task.domain;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Data
@Document
public class SearchCriteria {

    public static final String HITS_COUNT_FIELD = "hitsCount";
    public static final String LAST_HIT_FIELD = "lastHit";
    public static final String LAST_UPDATE_FIELD = "lastUpdate";
    public static final String TRANSACTION_ID_FIELD = "transactionId";

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