package com.zephyr.location.domain;

import com.zephyr.location.util.Relations;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.Wither;
import org.neo4j.ogm.annotation.GeneratedValue;
import org.neo4j.ogm.annotation.Id;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Relationship;

import java.util.Set;

@Data
@NodeEntity
@NoArgsConstructor
@AllArgsConstructor
public class Place {

    @Id
    @Wither
    @GeneratedValue
    private Long id;
    private String name;
    private String canonicalName;

    @Relationship(type = Relations.RELATIVES)
    private Place parent;

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @Relationship(type = Relations.RELATIVES, direction = Relationship.INCOMING)
    private Set<Place> children;

    @Relationship(type = Relations.COUNTRY, direction = Relationship.UNDIRECTED)
    private Country country;

    private PlaceType type;

    @SuppressWarnings("unused")
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
