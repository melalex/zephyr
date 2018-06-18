package com.zephyr.rating.services.impl;

import com.zephyr.rating.domain.Request;
import com.zephyr.rating.events.RatingUpdatedEvent;
import com.zephyr.rating.services.RatingNotificationService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.bus.ServiceMatcher;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@AllArgsConstructor
public class RatingNotificationServiceImpl implements RatingNotificationService {

    private static final String PUBLISH_RATING_UPDATE_MESSAGE = "Publish update for Rating: {}";

    private ApplicationEventPublisher publisher;
    private ServiceMatcher serviceMatcher;

    @Override
    public void publishRatingUpdatedEvent(Request request) {
        var serviceId = serviceMatcher.getServiceId();
        var event = new RatingUpdatedEvent(this, serviceId, serviceId);

        event.setRequest(request);

        log.info(PUBLISH_RATING_UPDATE_MESSAGE, request);

        publisher.publishEvent(event);
    }
}
