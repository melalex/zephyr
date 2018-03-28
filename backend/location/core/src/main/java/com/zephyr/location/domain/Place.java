package com.zephyr.location.domain;

import com.zephyr.location.domain.constants.Relations;
import lombok.Data;
import org.neo4j.ogm.annotation.*;

import java.util.Set;

@Data
@NodeEntity
public class Place {

    @Id
    @GeneratedValue
    private long id;
    private String name;
    private String canonicalName;

    @Relationship(type = Relations.RELATIVES, direction = Relationship.INCOMING)
    private Place parent;

    @Relationship(type = Relations.RELATIVES)
    private Set<Place> children;

    @Relationship(type = Relations.COUNTRY, direction = Relationship.UNDIRECTED)
    private Country country;

    private PlaceType type;

    public enum PlaceType {
        AIRPORT,
        AUTONOMOUS_COMMUNITY,
        BOROUGH,
        CANTON,
        CITY,
        CITY_REGION,
        CONGRESSIONAL_DISTRICT,
        COUNTRY,
        COUNTY,
        DEPARTMENT,
        DISTRICT,
        GOVERNORATE,
        MUNICIPALITY,
        NATIONAL_PARK,
        NEIGHBORHOOD,
        OKRUG,
        POSTAL_CODE,
        PREFECTURE,
        PROVINCE,
        REGION,
        STATE,
        TERRITORY,
        TV_REGION,
        UNION_TERRITORY,
        UNIVERSITY
    }
}
