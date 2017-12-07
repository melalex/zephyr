package com.zephyr.location.domain;

import lombok.Data;
import org.neo4j.ogm.annotation.GraphId;
import org.neo4j.ogm.annotation.NodeEntity;

@Data
@NodeEntity
public class Language {

    @GraphId
    private long iso;
    private String name;
}
