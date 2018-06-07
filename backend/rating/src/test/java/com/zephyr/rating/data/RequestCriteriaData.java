package com.zephyr.rating.data;

import com.zephyr.data.util.ConversionUtils;
import com.zephyr.rating.domain.RequestCriteria;
import com.zephyr.test.Criteria;
import com.zephyr.test.Statistic;
import com.zephyr.test.Tasks;
import lombok.AllArgsConstructor;

import java.util.List;

@AllArgsConstructor
public final class RequestCriteriaData {

    private QueryEntities queries;

    public List<RequestCriteria> simpleList() {
        return List.of(simple());
    }

    public RequestCriteria simple() {
        return RequestCriteria.builder()
                .url(Tasks.SIMPLE_URL)
                .from(Statistic.SIMPLE_FROM)
                .query(queries.simple())
                .originalCriteriaId(Criteria.SIMPLE_ID)
                .engines(ConversionUtils.toString(Tasks.SIMPLE_ENGINES))
                .build();
    }
}
