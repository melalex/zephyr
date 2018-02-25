package com.zephyr.task.domain;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

@Data
@Document
public class Task {

    @Id
    private String id;
    private String name;
    private String userId;
    private String url;
    
    private Set<String> engines;

    private LocalDate from;
    private LocalDate to;

    private boolean isShared;

    @DBRef
    private List<SearchCriteria> searchCriteria;
}
