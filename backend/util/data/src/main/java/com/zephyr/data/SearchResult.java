package com.zephyr.data;

import com.zephyr.data.enums.SearchEngine;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class SearchResult {
    private Keyword keyword;
    private LocalDateTime timestamp;
    private SearchEngine provider;
    private List<String> links;
}
