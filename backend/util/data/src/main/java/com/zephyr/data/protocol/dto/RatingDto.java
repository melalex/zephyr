package com.zephyr.data.protocol.dto;

import com.zephyr.data.protocol.enums.SearchEngine;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class RatingDto {

    private String id;

    private int position;
    private String url;

    private SearchCriteriaDto queryCriteria;
    private LocalDateTime timestamp;
    private SearchEngine provider;
}
