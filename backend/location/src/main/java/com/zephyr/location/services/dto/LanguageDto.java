package com.zephyr.location.services.dto;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.hateoas.Identifiable;

import java.util.Map;

@Data
public class LanguageDto implements Identifiable<String> {
    private String iso;
    private String name;

    @Override
    public String getId() {
        return iso;
    }
}
