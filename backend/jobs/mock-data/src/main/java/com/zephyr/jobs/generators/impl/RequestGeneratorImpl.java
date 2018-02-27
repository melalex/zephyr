package com.zephyr.jobs.generators.impl;

import com.zephyr.jobs.generators.RequestGenerator;
import com.zephyr.rating.domain.Request;
import com.zephyr.task.domain.SearchCriteria;
import lombok.Setter;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@StepScope
public class RequestGeneratorImpl implements RequestGenerator {

    @Setter(onMethod = @__(@Value("#{jobParameters[T(com.zephyr.jobs.batch.BatchConfiguration).REQUEST_PER_CRITERIA_JOB_PARAM]}")))
    private int requestPerCriteria;

    @Override
    public List<Request> generate(SearchCriteria criteria) {
        return null;
    }
}
