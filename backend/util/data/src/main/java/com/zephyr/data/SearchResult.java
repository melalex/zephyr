package com.zephyr.data;

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
