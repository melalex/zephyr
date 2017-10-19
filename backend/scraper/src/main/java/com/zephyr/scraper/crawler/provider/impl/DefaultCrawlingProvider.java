package com.zephyr.scraper.crawler.provider.impl;

import com.zephyr.scraper.config.ConfigurationManager;
import com.zephyr.scraper.domain.Response;
import lombok.Setter;
import org.jsoup.Jsoup;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class DefaultCrawlingProvider extends AbstractCrawlingProvider {
    private static final String HREF_ATTR = "href";

    @Setter(onMethod = @__(@Autowired))
    private ConfigurationManager configurationManager;

    @Override
    protected List<String> parse(Response response) {
        String linkSelector = configurationManager
                .configFor(response.getProvider())
                .getLinkSelector();

        return response.getDocuments().stream()
                .flatMap(d -> Jsoup.parse(d).select(linkSelector).stream())
                .map(e -> e.attr(HREF_ATTR))
                .collect(Collectors.toList());
    }
}