package com.zephyr.data.dto;

import com.zephyr.data.Keyword;
import com.zephyr.data.enums.SearchEngine;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class SearchResultDto {
    private Keyword keyword;
    private LocalDateTime timestamp;
    private SearchEngine provider;
    private List<String> links;
}
