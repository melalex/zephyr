package com.zephyr.data;

import lombok.Data;

@Data
public class CountryDto {
    private String iso;
    private String name;
    private String localeGoogle;
}
