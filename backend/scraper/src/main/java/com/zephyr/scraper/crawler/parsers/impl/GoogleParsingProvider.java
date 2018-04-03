package com.zephyr.scraper.crawler.parsers.impl;

import com.zephyr.scraper.crawler.parsers.ParsingProvider;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class GoogleParsingProvider implements ParsingProvider {

    @Override
    public List<String> parse(Document document, String linkSelector) {
        return document
                .select(linkSelector).stream()
                .map(Element::text)
                .collect(Collectors.toList());
    }
}
