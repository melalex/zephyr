package com.zephyr.rating.bus;

import com.zephyr.commons.interfaces.EventPublisher;
import com.zephyr.rating.events.RatingUpdatedEvent;
import com.zephyr.rating.domain.Request;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RefreshScope
public class RatingUpdatePublisher implements EventPublisher<Request> {

    private static final String PUBLISH_RATING_UPDATE_MESSAGE = "Publish update for Rating: {}";

    @Setter(onMethod = @__(@Autowired))
    private ApplicationEventPublisher publisher;

    @Setter(onMethod = @__(@Value("spring.application.name")))
    private String serviceName;

    @Override
    public void publish(Request request) {
        RatingUpdatedEvent event = new RatingUpdatedEvent(this, serviceName, serviceName);
        event.setRequest(request);

        publisher.publishEvent(event);

        log.info(PUBLISH_RATING_UPDATE_MESSAGE, request.getId());
    }
}
