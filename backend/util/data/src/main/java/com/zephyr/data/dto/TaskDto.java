package com.zephyr.data.dto;

import com.zephyr.data.commons.Keyword;
import com.zephyr.data.enums.SearchEngine;
import lombok.Data;

import java.util.Set;

@Data
public class TaskDto {
    private String url;
    private String ratingId;
    private Set<Keyword> keywords;
    private Set<SearchEngine> engines;

    private boolean onlyFromSpecifiedUserAgent;
}