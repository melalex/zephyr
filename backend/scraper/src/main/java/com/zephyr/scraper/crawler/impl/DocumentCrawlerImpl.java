package com.zephyr.scraper.crawler.impl;

import com.zephyr.data.SearchResult;
import com.zephyr.scraper.config.ConfigurationManager;
import com.zephyr.scraper.crawler.DocumentCrawler;
import com.zephyr.scraper.domain.ResponseDocument;
import lombok.Setter;
import org.joda.time.DateTime;
import org.jsoup.nodes.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@RefreshScope
public class DocumentCrawlerImpl implements DocumentCrawler {
    private static final String HREF_ATTR = "href";

    @Setter(onMethod = @__(@Autowired))
    private ConfigurationManager configurationManager;

    @Override
    public SearchResult crawl(ResponseDocument document) {
        String linkSelector = configurationManager
                .configFor(document.getProvider())
                .getLinkSelector();

        SearchResult searchResult = new SearchResult();

        searchResult.setKeyword(document.getKeyword());
        searchResult.setProvider(document.getProvider());
        searchResult.setTimestamp(DateTime.now());
        searchResult.setLinks(parse(document.getDocument(), linkSelector));

        return searchResult;
    }

    private List<String> parse(Document document, String linkSelector) {
        return document
                .select(linkSelector)
                .stream()
                .map(e -> e.attr(HREF_ATTR))
                .collect(Collectors.toList());
    }

}