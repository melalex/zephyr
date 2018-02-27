package com.zephyr.jobs.generators.impl;

import com.zephyr.jobs.generators.MeteredSearchCriteriaGenerator;
import com.zephyr.task.domain.MeteredSearchCriteria;
import com.zephyr.task.domain.SearchCriteria;
import org.springframework.stereotype.Component;

@Component
public class MeteredSearchCriteriaGeneratorImpl implements MeteredSearchCriteriaGenerator {

    @Override
    public MeteredSearchCriteria generate(SearchCriteria criteria) {
        return null;
    }
}
