package com.zephyr.task.domain;

import com.zephyr.data.commons.Keyword;
import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Data
@Document
public class Task {
    private String userId;
    private String url;
    private List<Keyword> keyword;
}
