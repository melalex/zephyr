package com.zephyr.location.domain;

import com.zephyr.data.enums.PlaceType;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document
public class Place {

    @Id
    private long id;
    private String name;
    private String canonicalName;
    private long parent;
    private String countryIso;
    private PlaceType type;
    private String location;
}
