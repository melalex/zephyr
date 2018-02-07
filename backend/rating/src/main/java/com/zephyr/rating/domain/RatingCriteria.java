package com.zephyr.rating.domain;

import com.zephyr.data.enums.SearchEngine;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Wither;

import java.time.LocalDate;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RatingCriteria {
    private String url;
    private LocalDate from;
    private LocalDate to;

    private Set<SearchEngine> engines;

    @Wither
    private Query query;
}
