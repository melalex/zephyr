package com.zephyr.data.dto;

import com.zephyr.data.enums.SearchEngine;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class RatingDto {
    private String id;

    private int position;
    private String url;

    private SearchCriteriaDto query;
    private LocalDateTime timestamp;
    private SearchEngine provider;
}
