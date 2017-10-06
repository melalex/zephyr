package com.zephyr.dictionary.tasks;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RefreshScope
public class UpdateRatingTask {

    @Scheduled(cron = "${task.updateRating.cron}")
    public void perform() {
        log.info("Performing update rating task");
    }
}
