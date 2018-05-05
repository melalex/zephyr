package com.zephyr.rating.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class RequestCriteria {

    private String originalCriteriaId;
    private Query query;
    private String url;
    private LocalDate from;
    private LocalDate to;
    private Set<String> engines;
}
