package com.zephyr.keyword.services.provider.impl;

import com.zephyr.data.protocol.request.KeywordRequest;
import com.zephyr.data.protocol.dto.KeywordDto;
import com.zephyr.keyword.clients.RatingServiceClient;
import com.zephyr.keyword.services.provider.KeywordsProvider;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;

@Component
public class RatingServiceProvider implements KeywordsProvider {

    @Setter(onMethod = @__(@Autowired))
    private RatingServiceClient ratingServiceClient;

    @Override
    public Flux<KeywordDto> provide(KeywordRequest request) {
        return ratingServiceClient.findRatingForUrlAsync(request.getUrl(), request.getPage(), request.getPageSize());
    }
}
