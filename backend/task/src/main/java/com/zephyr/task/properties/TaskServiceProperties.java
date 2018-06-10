package com.zephyr.task.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Component;

import java.time.Duration;

@Data
@Component
@RefreshScope
@ConfigurationProperties(prefix = "task")
public class TaskServiceProperties {

    private int batchSize;
    private boolean enableUpdates;
    private String cron;
    private RelevancePeriod relevancePeriod = new RelevancePeriod();

    public Duration getRelevancePeriodDuration() {
        return relevancePeriod.asDuration();
    }

    @Data
    private static class RelevancePeriod {

        private int days;
        private int hours;
        private int minutes;
        private int seconds;

        public Duration asDuration() {
            return Duration.ofDays(days)
                    .plus(Duration.ofHours(hours))
                    .plus(Duration.ofMinutes(minutes))
                    .plus(Duration.ofSeconds(seconds));
        }
    }
}
