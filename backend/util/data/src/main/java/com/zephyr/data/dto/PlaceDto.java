package com.zephyr.data.dto;

import lombok.Data;

@Data
public class PlaceDto {
    private long id;
    private String name;
    private String canonicalName;
    private long parent;
    private CountryDto country;
    private PlaceType type;
    private String location;

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
