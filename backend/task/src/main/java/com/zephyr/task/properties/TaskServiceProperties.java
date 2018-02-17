package com.zephyr.task.properties;

import lombok.Data;
import org.joda.time.MutablePeriod;
import org.joda.time.ReadablePeriod;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Component;

@Data
@Component
@RefreshScope
@ConfigurationProperties(prefix = "task")
public class TaskServiceProperties {
    private int batchSize;
    private boolean enableUpdates;
    private String cron;
    private RelevancePeriod relevancePeriod;

    public ReadablePeriod getRelevancePeriod() {
        MutablePeriod mutablePeriod = new MutablePeriod();
        mutablePeriod.setDays(relevancePeriod.getDays());
        mutablePeriod.setHours(relevancePeriod.getHours());
        mutablePeriod.setMinutes(relevancePeriod.getMinutes());
        mutablePeriod.setSeconds(relevancePeriod.getSeconds());

        return mutablePeriod.toPeriod();
    }

    @Data
    private static class RelevancePeriod {
        private int days;
        private int hours;
        private int minutes;
        private int seconds;
    }
}
