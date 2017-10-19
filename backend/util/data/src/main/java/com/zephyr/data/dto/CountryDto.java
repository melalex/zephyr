package com.zephyr.data.dto;

import lombok.Data;

@Data
public class CountryDto {
    private String iso;
    private String name;
    private String localeGoogle;
    private String localeYandex;
}
