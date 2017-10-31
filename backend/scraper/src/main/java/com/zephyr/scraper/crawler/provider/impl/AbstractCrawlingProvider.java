package com.zephyr.scraper.crawler.provider.impl;

import com.zephyr.data.commons.Keyword;
import com.zephyr.data.dto.SearchResultDto;
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
    public SearchResultDto provide(Response response) {
        SearchResultDto searchResultDto = new SearchResultDto();

        searchResultDto.setKeyword(mapper.map(response.getTask(), Keyword.class));
        searchResultDto.setProvider(response.getProvider());
        searchResultDto.setTimestamp(LocalDateTime.now());
        searchResultDto.setLinks(parse(response));

        return searchResultDto;
    }

    protected abstract List<String> parse(Response response);
}
