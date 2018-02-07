package com.zephyr.keyword.services.provider.impl;

import com.zephyr.keyword.clients.RatingServiceClient;
import com.zephyr.keyword.protocol.KeywordRequest;
import com.zephyr.keyword.protocol.KeywordResponse;
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
    public Flux<KeywordResponse> provide(KeywordRequest request) {
        return ratingServiceClient.findRatingForUrl(request.getUrl(), request.getPage(), request.getPageSize())
                .map(r -> KeywordResponse.of(r.getQuery().getQuery(), r.getUrl()));
    }
}
