package com.zephyr.scraper.crawler.provider.impl;

import com.zephyr.data.SearchResult;
import com.zephyr.scraper.crawler.provider.CrawlingProvider;
import com.zephyr.scraper.domain.ResponseDocument;
import lombok.AllArgsConstructor;
import org.joda.time.DateTime;
import org.jsoup.nodes.Document;

import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
public class CrawlingProviderImpl implements CrawlingProvider {
    private static final String HREF_ATTR = "href";

    private String linkSelector;

    @Override
    public SearchResult provide(ResponseDocument document) {
        SearchResult searchResult = new SearchResult();

        searchResult.setKeyword(document.getKeyword());
        searchResult.setProvider(document.getProvider());
        searchResult.setTimestamp(DateTime.now());
        searchResult.setLinks(parse(document.getDocument()));

        return searchResult;
    }

    private List<String> parse(Document document) {
        return document
                .select(linkSelector)
                .stream()
                .map(e -> e.attr(HREF_ATTR))
                .collect(Collectors.toList());
    }
}
