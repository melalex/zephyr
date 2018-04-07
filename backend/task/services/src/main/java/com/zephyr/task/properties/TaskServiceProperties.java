package com.zephyr.task.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.temporal.TemporalAmount;

@Data
@Component
@RefreshScope
@ConfigurationProperties(prefix = "task")
public class TaskServiceProperties {

    private int batchSize;
    private boolean enableUpdates;
    private String cron;
    private RelevancePeriod relevancePeriod;

    public TemporalAmount getRelevancePeriod() {
        return Duration.ofDays(relevancePeriod.getDays())
                .plus(Duration.ofHours(relevancePeriod.getHours()))
                .plus(Duration.ofMinutes(relevancePeriod.getMinutes()))
                .plus(Duration.ofSeconds(relevancePeriod.getSeconds()));
    }

    @Data
    private static class RelevancePeriod {

        private int days;
        private int hours;
        private int minutes;
        private int seconds;
    }
}
