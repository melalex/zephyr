package com.zephyr.task.properties;

import lombok.Data;
import org.joda.time.Period;
import org.joda.time.ReadablePeriod;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Component;

@Data
@Component
@RefreshScope
@ConfigurationProperties(prefix = "integration")
public class TaskServiceProperties {
    private String cron;
    private int batchSize;
    private int relevancePeriodDays;
    private int relevancePeriodHours;
    private int relevancePeriodMinutes;
    private int relevancePeriodSeconds;

    public ReadablePeriod getRelevancePeriod() {
        return new Period(0, 0, 0, relevancePeriodDays, relevancePeriodHours,
                relevancePeriodMinutes, relevancePeriodSeconds, 0);
    }
}
