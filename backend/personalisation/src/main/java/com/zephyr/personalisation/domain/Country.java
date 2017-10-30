package com.zephyr.personalisation.domain;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document
public class Country {

    @Id
    private String iso;
    private String name;
    private String localeGoogle;
    private String localeYandex;
}