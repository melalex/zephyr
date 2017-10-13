package com.zephyr.location.domain;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Map;

@Data
@Document
public class Language {

    @Id
    private String iso;
    private Map<String, String> name;
}
