package com.zephyr.scraper.crawler.parsers;

import com.zephyr.scraper.locator.SearchEngineProvider;
import org.jsoup.nodes.Document;

import java.util.List;

public interface ParsingProvider extends SearchEngineProvider {

    List<String> parse(Document document, String linkSelector);
}
