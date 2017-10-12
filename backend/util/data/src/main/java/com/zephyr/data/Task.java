package com.zephyr.data;

import lombok.Data;

import java.util.List;

@Data
public class Task {
    private String monitoringId;
    private String target;
    private List<Keyword> keywords;
}