package com.zephyr.rating.services;

import com.zephyr.data.protocol.dto.KeywordDto;
import com.zephyr.rating.domain.vo.SearchResultVo;
import reactor.core.publisher.Flux;

public interface RatingService {

    Flux<KeywordDto> findRatingForUrl(String url, int page, int size);

    void save(SearchResultVo target);
}