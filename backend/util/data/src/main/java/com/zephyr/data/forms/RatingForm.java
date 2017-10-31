package com.zephyr.data.forms;

import com.zephyr.data.commons.RatingEntry;
import com.zephyr.data.criteria.UserAgentCriteria;
import com.zephyr.data.enums.RatingState;
import com.zephyr.data.enums.SearchEngine;
import lombok.Data;

import java.util.Set;

@Data
public class RatingForm {
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