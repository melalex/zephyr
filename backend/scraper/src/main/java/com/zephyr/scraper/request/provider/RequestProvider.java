package com.zephyr.scraper.request.provider;

import com.zephyr.data.dto.QueryDto;
import com.zephyr.scraper.domain.EngineRequest;

import java.util.List;

@FunctionalInterface
public interface RequestProvider {

    List<EngineRequest> provide(QueryDto query);
}
