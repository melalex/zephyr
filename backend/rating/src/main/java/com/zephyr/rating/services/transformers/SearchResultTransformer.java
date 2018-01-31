package com.zephyr.rating.services.transformers;

import com.zephyr.commons.interfaces.Transformer;
import com.zephyr.data.dto.SearchResultDto;
import com.zephyr.rating.domain.Rating;

public class SearchResultTransformer implements Transformer<SearchResultDto, Iterable<Rating>> {

    @Override
    public Iterable<Rating> transform(SearchResultDto source) {
        return null;
    }
}
