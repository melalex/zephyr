package com.zephyr.rating.services.impl;

import com.zephyr.rating.domain.Request;
import com.zephyr.rating.events.RatingUpdatedEvent;
import com.zephyr.rating.services.RatingNotificationService;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class RatingNotificationServiceImpl implements RatingNotificationService {

    private static final String PUBLISH_RATING_UPDATE_MESSAGE = "Publish update for Rating with id [{}]";

    @Setter(onMethod = @__(@Autowired))
    private ApplicationEventPublisher publisher;

    @Setter(onMethod = @__(@Value("spring.application.name")))
    private String serviceName;

    @Override
    public void publishRatingUpdatedEvent(Request request) {
        RatingUpdatedEvent event = new RatingUpdatedEvent(this, serviceName, serviceName);
        event.setRequest(request);

        publisher.publishEvent(event);

        log.info(PUBLISH_RATING_UPDATE_MESSAGE, request.getId());
    }
}
