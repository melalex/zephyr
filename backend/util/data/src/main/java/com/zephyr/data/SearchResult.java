package com.zephyr.data;

import com.zephyr.data.enums.SearchEngine;
import lombok.Builder;
import lombok.Data;
import org.joda.time.DateTime;

import java.util.List;

@Data
public class SearchResult {
    private Keyword keyword;
    private DateTime timestamp;
    private SearchEngine provider;
    private List<String> links;
}
