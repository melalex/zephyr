package com.zephyr.task.domain;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;
import java.util.Set;

@Data
@Document
public class Task {

    public static final String ID_FIELD = "id";
    public static final String USER_ID_FIELD = "userId";
    public static final String SHARED_FIELD = "shared";
    @Id
    private String id;
    private String name;
    private String userId;
    private String url;

    private Set<SearchEngine> engines;

    private boolean shared;

    @DBRef
    private List<SearchCriteria> searchCriteria;
}
