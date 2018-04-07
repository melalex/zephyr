package com.zephyr.scraper.factories;

import com.zephyr.data.internal.dto.QueryDto;
import com.zephyr.data.internal.dto.SearchResultDto;
import com.zephyr.scraper.domain.EngineRequest;
import lombok.Setter;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.Clock;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Component
public class SearchResultFactory {

    @Setter(onMethod = @__(@Autowired))
    private Clock clock;

    @Setter(onMethod = @__(@Autowired))
    private ModelMapper modelMapper;

    public SearchResultDto create(EngineRequest request, List<String> links) {
        SearchResultDto searchResult = new SearchResultDto();
        searchResult.setId(UUID.randomUUID().toString());
        searchResult.setOffset(request.getOffset());
        searchResult.setQuery(modelMapper.map(request.getQuery(), QueryDto.class));
        searchResult.setProvider(request.getProvider());
        searchResult.setTimestamp(LocalDateTime.now(clock));
        searchResult.setLinks(links);

        return searchResult;
    }
}
