package com.zephyr.data.protocol.dto;

import com.zephyr.data.protocol.enums.PlaceType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PlaceDto {

    private Long id;
    private Long parent;
    private String name;
    private String canonicalName;
    private CountryDto country;
    private PlaceType type;
    private String uule;
}
