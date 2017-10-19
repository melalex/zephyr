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
    private String iso;
    private String name;
    private String localeGoogle;
    private String localeYandex;
}