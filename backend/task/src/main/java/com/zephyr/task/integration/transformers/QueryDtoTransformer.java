package com.zephyr.task.integration.transformers;

import com.zephyr.data.dto.QueryDto;
import com.zephyr.task.domain.SearchCriteria;
import org.springframework.integration.transformer.GenericTransformer;
import org.springframework.stereotype.Component;

@Component
public class QueryDtoTransformer implements GenericTransformer<SearchCriteria, QueryDto> {

    @Override
    public QueryDto transform(SearchCriteria source) {
        return null;
    }
}
