package com.zephyr.scraper.crawler.parsers.impl;

import com.zephyr.data.protocol.enums.SearchEngine;
import com.zephyr.scraper.crawler.parsers.ParsingProvider;
import com.zephyr.scraper.locator.AbstractSearchEngineProvider;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.nodes.Document;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;

@Slf4j
@Component
public class DuckDuckGoParsingProvider extends AbstractSearchEngineProvider implements ParsingProvider {
    private static final String WARNING = "DuckDuckGo Crawler not implemented yet";

    public DuckDuckGoParsingProvider() {
        super(SearchEngine.DUCKDUCKGO);
    }

    @Override
    public List<String> parse(Document document, String linkSelector) {
        log.warn(WARNING);
        return Collections.emptyList();
    }
}