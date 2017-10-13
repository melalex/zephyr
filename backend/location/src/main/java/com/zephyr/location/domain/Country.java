package com.zephyr.location.domain;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Map;

@Data
@Document
public class Country {

    @Id
    private String id;

    @Indexed
    private String iso2;

    private String iso3;

    private int isoNumeric;

    private String localeGoogle;

    private Map<String, String> name;
}
