package com.zephyr.task.domain;

import com.zephyr.task.domain.criteria.PlaceCriteria;
import com.zephyr.task.domain.criteria.UserAgentCriteria;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document
public class SearchCriteria {

    @Id
    private String id;

    private String query;
    private PlaceCriteria place;
    private String languageIso;
    private UserAgentCriteria userAgent;
}