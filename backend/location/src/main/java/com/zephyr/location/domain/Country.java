package com.zephyr.location.domain;

import lombok.Data;
import org.neo4j.ogm.annotation.Id;
import org.neo4j.ogm.annotation.NodeEntity;

@Data
@NodeEntity
public class Country {

    @Id
    private String iso;
    private String name;
    private String localeGoogle;
    private String localeYandex;
}