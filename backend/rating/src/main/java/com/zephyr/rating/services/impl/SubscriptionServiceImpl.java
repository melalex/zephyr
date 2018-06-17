package com.zephyr.rating.services.impl;

import com.zephyr.commons.interfaces.Matcher;
import com.zephyr.rating.domain.Request;
import com.zephyr.rating.domain.RequestCriteria;
import com.zephyr.rating.events.RatingUpdatedEvent;
import com.zephyr.rating.services.SubscriptionService;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.TopicProcessor;

@Slf4j
@Service
public class SubscriptionServiceImpl implements SubscriptionService {

    private final TopicProcessor<Request> hotSource = TopicProcessor.create();
    private final Flux<Request> hotFlux = hotSource.publish().autoConnect().cache(256).log();

    @Setter(onMethod = @__(@Autowired))
    private Matcher<RequestCriteria, Request> requestMatcher;

    @EventListener
    public void onRatingUpdated(RatingUpdatedEvent event) {
        Request request = event.getRequest();
        log.info("Received new Rating: {}", request);
        hotSource.onNext(request);
    }

    @Override
    public Flux<Request> subscribe(RequestCriteria requestCriteria) {
        return Flux.from(hotFlux)
                .filter(r -> requestMatcher.matches(requestCriteria, r))
                .take(1);
    }
}
