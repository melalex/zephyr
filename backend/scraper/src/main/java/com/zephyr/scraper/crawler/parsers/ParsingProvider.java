package com.zephyr.scraper.crawler.parsers;

import org.jsoup.nodes.Document;

import java.util.List;

public interface ParsingProvider {

    List<String> parse(Document document, String linkSelector);
}
