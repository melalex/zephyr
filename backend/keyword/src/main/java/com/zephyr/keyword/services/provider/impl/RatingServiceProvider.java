package com.zephyr.keyword.services.provider.impl;

import com.zephyr.commons.templates.AbstractProvider;
import com.zephyr.data.protocol.criteria.KeywordCriteria;
import com.zephyr.data.protocol.vo.KeywordVo;
import com.zephyr.keyword.clients.RatingServiceClient;
import com.zephyr.keyword.properties.KeywordSource;
import com.zephyr.keyword.services.provider.KeywordsProvider;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;

@Component
public class RatingServiceProvider extends AbstractProvider<KeywordSource> implements KeywordsProvider {

    @Setter(onMethod = @__(@Autowired))
    private RatingServiceClient ratingServiceClient;

    public RatingServiceProvider() {
        super(KeywordSource.RATING_SERVICE);
    }

    @Override
    public Flux<KeywordVo> provide(KeywordCriteria request) {
        return ratingServiceClient.findRatingForUrl(request.getUrl(), request.getPage(), request.getPageSize())
                .map(r -> KeywordVo.of(r.getQuery().getQuery(), r.getUrl()));
    }
}
