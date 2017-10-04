package com.zephyr.rating.domain;

import com.zephyr.commons.data.Keyword;
import lombok.Data;
import org.joda.time.DateTime;
import org.joda.time.LocalDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Map;
import java.util.Set;

@Data
@Document
public class Monitoring {

    @Id
    private String id;

    @LastModifiedDate
    private DateTime lastUpdate;

    @Transient
    private State state;

    private String userId;
    private String target;
    private Set<MonitoringEntry> entries;

    @Data
    public static class MonitoringEntry {
        private Keyword keyword;
        private Map<LocalDate, Integer> statistic;
    }

    public enum State {
        CREATED,
        UPDATED,
        OUT_OF_DATE,
        UPDATING
    }
}