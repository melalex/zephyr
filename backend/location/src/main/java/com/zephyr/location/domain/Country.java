package com.zephyr.location.domain;

import com.zephyr.location.domain.constants.Relations;
import lombok.Data;
import org.neo4j.ogm.annotation.GraphId;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Relationship;

import java.util.Set;

@Data
@NodeEntity
public class Country {
    private static final String RELATIONSHIP_COUNTRY = "COUNTRY";

    @GraphId
    private String iso;
    private String name;
    private String localeGoogle;
    private String localeYandex;

    @Relationship(type = Relations.COUNTRY, direction = Relationship.UNDIRECTED)
    private Set<Place> places;
}