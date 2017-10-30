package com.zephyr.scraper.crawler.provider.impl;

import com.zephyr.data.Keyword;
import com.zephyr.data.SearchResult;
import com.zephyr.scraper.crawler.provider.CrawlingProvider;
import com.zephyr.scraper.domain.Response;
import lombok.Setter;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;
import java.util.List;

public abstract class AbstractCrawlingProvider implements CrawlingProvider {

    @Setter(onMethod = @__(@Autowired))
    private ModelMapper mapper;

    @Override
    public SearchResult provide(Response response) {
        SearchResult searchResult = new SearchResult();

        searchResult.setKeyword(mapper.map(response.getTask(), Keyword.class));
        searchResult.setProvider(response.getProvider());
        searchResult.setTimestamp(LocalDateTime.now());
        searchResult.setLinks(parse(response));

        return searchResult;
    }

    protected abstract List<String> parse(Response response);
}
