package com.zephyr.rating.domain;

import com.zephyr.data.enums.SearchEngine;
import cz.jirutka.validator.collection.constraints.EachPattern;
import cz.jirutka.validator.collection.constraints.EachSize;
import lombok.Data;
import org.hibernate.validator.constraints.URL;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Set;

@Data
public class TaskForm {

    @URL
    private String url;

    @NotEmpty
    @EachSize(min = 1, max = 64)
    @EachPattern(regexp = "^[a-zA-Z0-9]$")
    private Set<String> keywords;

    @NotEmpty
    private Set<SearchEngine> engines;

    @NotNull
    private String place;

    @NotNull
    private String countryIso;

    @NotNull
    private String languageIso;

    @Valid
    private UserAgentForm userAgent;

    private boolean onlyFromSpecifiedUserAgent;
}