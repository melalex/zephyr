package com.zephyr.location.services.dto;

import lombok.Data;
import org.springframework.hateoas.Identifiable;

@Data
public class CountryDto implements Identifiable<String> {
    private String iso;
    private String name;
    private String localeGoogle;

    @Override
    public String getId() {
        return iso;
    }
}
