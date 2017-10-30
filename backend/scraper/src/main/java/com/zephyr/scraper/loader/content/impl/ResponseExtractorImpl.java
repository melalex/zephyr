package com.zephyr.scraper.loader.content.impl;

import com.google.common.collect.ImmutableMap;
import com.zephyr.commons.MapUtils;
import com.zephyr.data.enums.SearchEngine;
import com.zephyr.scraper.domain.PageResponse;
import com.zephyr.scraper.loader.content.ResponseExtractor;
import com.zephyr.scraper.loader.content.provider.ContentProvider;
import com.zephyr.scraper.loader.content.provider.impl.DuckDuckGoContentProvider;
import com.zephyr.scraper.loader.content.provider.impl.YandexContentProvider;
import org.jsoup.Jsoup;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Map;

@Component
public class ResponseExtractorImpl implements ResponseExtractor {
    private Map<SearchEngine, ContentProvider> providers;

    @PostConstruct
    public void init() {
        providers = ImmutableMap.<SearchEngine, ContentProvider>builder()
                .put(SearchEngine.DUCKDUCKGO, new DuckDuckGoContentProvider())
                .put(SearchEngine.YANDEX, new YandexContentProvider())
                .build();
    }

    @Override
    public PageResponse extract(MediaType type, String content, SearchEngine engine, int number) {
        return PageResponse.of(type, parse(type, engine, content), number);
    }

    private Object parse(MediaType type, SearchEngine engine, String content) {
        if (MediaType.TEXT_HTML.equals(type)) {
            return Jsoup.parse(content);
        }

        return MapUtils.getOrThrow(providers, engine);
    }
}
