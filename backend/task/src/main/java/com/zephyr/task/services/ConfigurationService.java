package com.zephyr.task.services;

import org.springframework.data.domain.Pageable;

import java.time.temporal.TemporalAmount;

public interface ConfigurationService {

    Pageable searchCriteriaOrder();

    TemporalAmount getRelevancePeriod();

    String getCron();
}
