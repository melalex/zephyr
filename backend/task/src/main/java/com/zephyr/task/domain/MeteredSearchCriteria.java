package com.zephyr.task.domain;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.mapping.DBRef;

import java.time.LocalDateTime;

@Data
public class MeteredSearchCriteria {

    @Id
    private String id;

    @DBRef
    private SearchCriteria searchCriteria;

    @LastModifiedDate
    private LocalDateTime lastHit;
    private LocalDateTime lastUpdate;
    private long hitsCount;
    private String transactionId;
}