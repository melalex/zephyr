package com.zephyr.scraper.crawler.provider.impl;

import org.apache.commons.lang3.StringUtils;
import org.jsoup.nodes.Document;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class BingCrawlingProvider extends AbstractCrawlingProvider {
    private static final String HREF = "href";

    @Override
    protected List<String> parse(Document document, String linkSelector) {
        return document
                .select(linkSelector).stream()
                .map(e -> e.attr(HREF))
                .filter(StringUtils::isNotBlank)
                .collect(Collectors.toList());
    }
}
