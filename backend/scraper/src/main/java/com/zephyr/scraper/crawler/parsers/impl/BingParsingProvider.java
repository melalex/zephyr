package com.zephyr.scraper.crawler.parsers.impl;

import com.zephyr.data.protocol.enums.SearchEngine;
import com.zephyr.scraper.crawler.parsers.ParsingProvider;
import com.zephyr.scraper.locator.AbstractSearchEngineProvider;
import org.apache.commons.lang3.StringUtils;
import org.jsoup.nodes.Document;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class BingParsingProvider extends AbstractSearchEngineProvider implements ParsingProvider {
    private static final String HREF = "href";

    public BingParsingProvider() {
        super(SearchEngine.BING);
    }

    @Override
    public List<String> parse(Document document, String linkSelector) {
        return document
                .select(linkSelector).stream()
                .map(e -> e.attr(HREF))
                .filter(StringUtils::isNotBlank)
                .collect(Collectors.toList());
    }
}
