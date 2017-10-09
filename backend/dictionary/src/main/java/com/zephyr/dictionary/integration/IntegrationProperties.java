package com.zephyr.dictionary.integration;

import lombok.Getter;
import org.joda.time.Period;
import org.joda.time.ReadablePeriod;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Component;

@Component
@RefreshScope
public class IntegrationProperties {

    @Getter
    @Value("${integration.updateRating.cron}")
    private String cron;

    @Getter
    @Value("${integration.updateRating.batchSize}")
    private int batchSize;

    @Value("${integration.updateRating.relevancePeriod.days}")
    private int relevancePeriodDays;

    @Value("${integration.updateRating.relevancePeriod.hours}")
    private int relevancePeriodHours;

    @Value("${integration.updateRating.relevancePeriod.minutes}")
    private int relevancePeriodMinutes;

    @Value("${integration.updateRating.relevancePeriod.seconds}")
    private int relevancePeriodSeconds;

    public ReadablePeriod getRelevancePeriod() {
        return new Period(0, 0, 0, relevancePeriodDays, relevancePeriodHours, relevancePeriodMinutes, relevancePeriodSeconds, 0);
    }
}
