package com.zephyr.scraper.crawler.parsers.impl;

import com.zephyr.scraper.crawler.parsers.ParsingProvider;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.nodes.Document;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;

@Slf4j
@Component
public class DuckDuckGoParsingProvider implements ParsingProvider {

    private static final String WARNING = "DuckDuckGo Crawler not implemented yet";

    @Override
    public List<String> parse(Document document, String linkSelector) {
        log.warn(WARNING);
        return Collections.emptyList();
    }
}