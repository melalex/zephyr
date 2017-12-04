package com.zephyr.scheduling;

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import reactor.core.scheduler.Scheduler;
import reactor.core.scheduler.Schedulers;

import java.time.Clock;

@Configuration
@ComponentScan
public class SchedulingConfiguration {

    @Bean
    @ConditionalOnMissingBean
    public Scheduler scheduler() {
        return Schedulers.elastic();
    }

    @Bean
    @ConditionalOnMissingBean
    public Clock clock() {
        return Clock.systemDefaultZone();
    }
}
