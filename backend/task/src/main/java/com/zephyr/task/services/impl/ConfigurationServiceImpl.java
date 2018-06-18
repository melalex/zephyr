package com.zephyr.task.services.impl;

import com.zephyr.task.properties.TaskServiceProperties;
import com.zephyr.task.services.ConfigurationService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.temporal.TemporalAmount;

@Service
@AllArgsConstructor
public class ConfigurationServiceImpl implements ConfigurationService {

    private static final int FIRST_PAGE = 0;

    private static final String HITS_COUNT_FIELD = "hitsCount";
    private static final String LAST_HIT_FIELD = "lastHit";
    private static final String LAST_UPDATE_FIELD = "lastUpdate";

    private TaskServiceProperties taskServiceProperties;

    @Override
    public Pageable searchCriteriaOrder() {
        var sort = Sort.by(
                Sort.Order.desc(HITS_COUNT_FIELD),
                Sort.Order.desc(LAST_HIT_FIELD),
                Sort.Order.asc(LAST_UPDATE_FIELD)
        );

        return PageRequest.of(FIRST_PAGE, taskServiceProperties.getBatchSize(), sort);
    }

    @Override
    public TemporalAmount getRelevancePeriod() {
        return taskServiceProperties.getRelevancePeriodDuration();
    }

    @Override
    public String getCron() {
        return taskServiceProperties.getCron();
    }
}
