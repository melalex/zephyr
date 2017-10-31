package com.zephyr.task.services;

import com.zephyr.data.dto.RatingDto;
import org.springframework.http.codec.ServerSentEvent;
import reactor.core.publisher.Flux;

public interface NotificationService {

    Flux<ServerSentEvent<RatingDto>> subscribeOnUpdates(String user);
}
