package com.zephyr.keyword.business.services.provider.impl;

import com.zephyr.data.protocol.criteria.KeywordCriteria;
import com.zephyr.data.protocol.vo.KeywordVo;
import com.zephyr.keyword.business.clients.RatingServiceClient;
import com.zephyr.keyword.business.services.provider.KeywordsProvider;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;

@Component
public class RatingServiceProvider implements KeywordsProvider {

    @Setter(onMethod = @__(@Autowired))
    private RatingServiceClient ratingServiceClient;

    @Override
    public Flux<KeywordVo> provide(KeywordCriteria request) {
        return ratingServiceClient.findRatingForUrl(request.getUrl(), request.getPage(), request.getPageSize())
                .map(r -> KeywordVo.of(r.getQuery().getQuery(), r.getUrl()));
    }
}
