package com.zephyr.rating.domain;

import com.zephyr.data.dto.TaskDto;
import com.zephyr.rating.services.dto.RatingDto;
import lombok.Value;
import reactor.core.publisher.Flux;
import reactor.core.publisher.UnicastProcessor;

@Value
public class Subscription {
    private UnicastProcessor<RatingDto> processor;
    private TaskDto task;

    private Subscription(UnicastProcessor<RatingDto> processor, TaskDto task) {
        this.processor = processor;
        this.task = task;
    }

    public static Subscription of(TaskDto task) {
        return new Subscription(UnicastProcessor.create(), task);
    }

    public boolean isSubscribed(RatingDto ratingDto) {
        return false;
    }

    public Flux<RatingDto> connect() {
        return processor.publish()
                .autoConnect()
                .filter(this::isSubscribed);
    }
}
