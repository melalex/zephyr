package com.zephyr.rating.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class RequestCriteria {

    private String originalCriteriaId;
    private QueryCriteria queryCriteria;
    private String url;
    private LocalDate from;
    private LocalDate to;
    private String engine;
}
