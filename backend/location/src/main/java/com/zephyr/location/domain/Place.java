package com.zephyr.location.domain;

import com.zephyr.location.domain.constants.Relations;
import lombok.Data;
import org.neo4j.ogm.annotation.GraphId;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Relationship;

import java.util.Set;

@Data
@NodeEntity
public class Place {

    @GraphId
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
    private String location;

    public enum PlaceType {
        AIRPORT("Airport"),
        AUTONOMOUS_COMMUNITY("Autonomous Community"),
        BOROUGH("Borough"),
        CANTON("Canton"),
        CITY("City"),
        CITY_REGION("City Region"),
        CONGRESSIONAL_DISTRICT("Congressional District"),
        COUNTRY("Country"),
        COUNTY("County"),
        DEPARTMENT("Department"),
        DISTRICT("District"),
        GOVERNORATE("Governorate"),
        MUNICIPALITY("Municipality"),
        NATIONAL_PARK("National Park"),
        NEIGHBORHOOD("Neighborhood"),
        OKRUG("Okrug"),
        POSTAL_CODE("Postal Code"),
        PREFECTURE("Prefecture"),
        PROVINCE("Province"),
        REGION("Region"),
        STATE("State"),
        TERRITORY("Territory"),
        TV_REGION("TV Region"),
        UNION_TERRITORY("Union Territory"),
        UNIVERSITY("University");

        private final String value;

        PlaceType(String value) {
            this.value = value;
        }

        public String getValue() {
            return value;
        }
    }
}
