package com.zephyr.dictionary.domain;

import com.zephyr.data.Keyword;
import lombok.Data;
import org.joda.time.DateTime;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document
public class Dictionary {

    @Id
    private String id;

    private Keyword keyword;
    private DateTime lastHit;
    private DateTime lastUpdate;
    private long hitsCount;
    private String transactionId;
}