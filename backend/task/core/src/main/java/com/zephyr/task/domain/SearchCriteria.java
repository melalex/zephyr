package com.zephyr.task.domain;

import com.zephyr.task.domain.criteria.PlaceCriteria;
import com.zephyr.task.domain.criteria.UserAgentCriteria;
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
    private PlaceCriteria place;
    private String languageIso;
    private UserAgentCriteria userAgent;
}