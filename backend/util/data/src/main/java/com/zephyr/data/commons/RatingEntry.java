package com.zephyr.data.commons;

import lombok.Data;

import java.time.LocalDate;
import java.util.Map;

@Data
public class RatingEntry {
    private String keyword;
    private Map<LocalDate, Integer> statistic;
}
