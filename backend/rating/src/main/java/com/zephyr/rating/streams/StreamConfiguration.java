package com.zephyr.rating.streams;

import com.zephyr.data.dto.RatingDto;
import com.zephyr.rating.services.RatingService;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.Input;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.cloud.stream.messaging.Sink;
import reactor.core.publisher.Flux;

@EnableBinding(Sink.class)
public class StreamConfiguration {

    @Setter(onMethod = @__(@Autowired))
    private RatingService ratingService;

    @StreamListener
    private Flux<Void> receiveRatingDto(@Input(Sink.INPUT) Flux<RatingDto> ratings) {
        return ratings.flatMap(ratingService::update);
    }
}