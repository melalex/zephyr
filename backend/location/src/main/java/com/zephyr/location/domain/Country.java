package com.zephyr.location.domain;

import com.zephyr.location.util.Relations;
import lombok.Data;
import org.neo4j.ogm.annotation.Id;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Relationship;

import java.util.Set;

@Data
@NodeEntity
public class Country {

    @Id
    private String iso;
    private String name;
    private String localeGoogle;
    private String localeYandex;

    @Relationship(type = Relations.COUNTRY, direction = Relationship.UNDIRECTED)
    private Set<Place> places;
}