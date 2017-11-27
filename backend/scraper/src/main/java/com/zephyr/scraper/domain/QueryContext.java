package com.zephyr.scraper.domain;

import com.zephyr.data.commons.Keyword;
import com.zephyr.data.dto.PlaceDto;
import com.zephyr.scraper.domain.immutable.ImmutableKeyword;
import com.zephyr.scraper.domain.immutable.ImmutablePlace;
import lombok.Value;
import lombok.experimental.Delegate;

@Value(staticConstructor = "of")
public class QueryContext {

    @Delegate(types = ImmutableKeyword.class)
    private Keyword keyword;

    @Delegate(types = ImmutablePlace.class)
    private PlaceDto placeDto;
}