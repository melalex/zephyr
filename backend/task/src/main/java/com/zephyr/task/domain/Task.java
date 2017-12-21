package com.zephyr.task.domain;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Data
@Document
public class Task {

    @Id
    private String id;
    private String userId;
    private String url;

    @DBRef
    private List<SearchCriteria> searchCriteria;
}
