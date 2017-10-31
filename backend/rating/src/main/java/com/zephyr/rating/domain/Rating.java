package com.zephyr.rating.domain;

import com.zephyr.data.commons.RatingEntry;
import com.zephyr.data.criteria.UserAgentCriteria;
import com.zephyr.data.enums.RatingState;
import com.zephyr.data.enums.SearchEngine;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.Set;

@Data
@Document
public class Rating {

    @Id
    private String id;

    private LocalDateTime lastUpdate;

    private String user;

    private String target;
    private RatingState state;
    private Set<RatingEntry> entries;
    private Set<SearchEngine> engines;

    private String place;
    private String countryIso;
    private String languageIso;
    private UserAgentCriteria userAgentCriteria;

    private boolean onlyFromSpecifiedUserAgent;
}