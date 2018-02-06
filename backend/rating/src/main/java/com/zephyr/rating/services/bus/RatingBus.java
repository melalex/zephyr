package com.zephyr.rating.services.bus;

import com.zephyr.data.dto.SearchCriteriaDto;
import com.zephyr.rating.domain.Query;
import com.zephyr.rating.services.dto.RatingDto;
import reactor.core.publisher.Flux;

public interface RatingBus {

    void onRatingUpdated(Query query);

    Flux<RatingDto> updatesFor(SearchCriteriaDto searchCriteria);
}
