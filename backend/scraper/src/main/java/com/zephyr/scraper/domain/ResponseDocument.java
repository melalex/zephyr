package com.zephyr.scraper.domain;

import com.zephyr.data.Keyword;
import com.zephyr.data.enums.SearchEngine;
import lombok.Value;
import org.jsoup.nodes.Document;

@Value(staticConstructor = "of")
public class ResponseDocument {
    private Keyword keyword;
    private SearchEngine provider;
    private Document document;
}
