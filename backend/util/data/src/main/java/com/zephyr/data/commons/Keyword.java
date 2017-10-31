package com.zephyr.data.commons;

import com.zephyr.data.criteria.UserAgentCriteria;
import lombok.Data;

@Data
public class Keyword {
    private String word;
    private String place;
    private String countryIso;
    private String languageIso;
    private UserAgentCriteria userAgentCriteria;
}