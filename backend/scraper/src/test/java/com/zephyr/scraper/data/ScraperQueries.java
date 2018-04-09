package com.zephyr.scraper.data;

import com.zephyr.scraper.domain.Query;
import com.zephyr.test.Queries;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;

@AllArgsConstructor(access = AccessLevel.PACKAGE)
public final class ScraperQueries {

    private Queries queries;
    private ModelMapper modelMapper;

    public Query simple() {
        return modelMapper.map(queries, Query.class);
    }
}
