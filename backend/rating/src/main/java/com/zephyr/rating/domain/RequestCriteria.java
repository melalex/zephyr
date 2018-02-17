package com.zephyr.rating.domain;

import com.zephyr.data.protocol.enums.SearchEngine;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Wither;

import java.time.LocalDate;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RequestCriteria {

    @Wither
    private Query query;
    private String url;
    private LocalDate from;
    private LocalDate to;
    private Set<SearchEngine> engines;
}
