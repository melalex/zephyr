package com.zephyr.jobs.generators;

import com.zephyr.task.domain.MeteredSearchCriteria;
import com.zephyr.task.domain.SearchCriteria;

public interface MeteredSearchCriteriaGenerator {

    MeteredSearchCriteria generate(SearchCriteria criteria);
}
