package com.zephyr.data.internal.dto;

import com.zephyr.data.protocol.enums.SearchEngine;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class SearchResultDto {
    private String id;
    private int offset;
    private QueryDto query;
    private LocalDateTime timestamp;
    private SearchEngine provider;
    private List<String> links;
}
