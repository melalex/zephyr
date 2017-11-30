package com.zephyr.data.dto;

import com.zephyr.data.enums.PlaceType;
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
}
