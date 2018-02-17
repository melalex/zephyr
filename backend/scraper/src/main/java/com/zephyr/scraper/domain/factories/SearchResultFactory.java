package com.zephyr.scraper.domain.factories;

import com.zephyr.data.internal.dto.SearchResultDto;
import com.zephyr.scraper.domain.EngineRequest;
import lombok.Setter;
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

    public SearchResultDto create(EngineRequest request, List<String> links) {
        SearchResultDto searchResult = new SearchResultDto();
        searchResult.setId(UUID.randomUUID().toString());
        searchResult.setOffset(request.getOffset());
        searchResult.setQuery(request.getQuery());
        searchResult.setProvider(request.getProvider());
        searchResult.setTimestamp(LocalDateTime.now(clock));
        searchResult.setLinks(links);

        return searchResult;
    }
}
