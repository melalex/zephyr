package com.zephyr.rating.services.impl;

import com.zephyr.commons.interfaces.Matcher;
import com.zephyr.rating.domain.Request;
import com.zephyr.rating.domain.RequestCriteria;
import com.zephyr.rating.events.RatingUpdatedEvent;
import com.zephyr.rating.services.SubscriptionService;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.UnicastProcessor;

import javax.annotation.PostConstruct;

@Slf4j
@Service
@RequiredArgsConstructor
public class SubscriptionServiceImpl implements SubscriptionService {

    private static final String RECEIVED_EVENT_MESSAGE = "Received new Rating with id [ {} ]";

    private UnicastProcessor<Request> hotSource;
    private Flux<Request> hotFlux;

    @NonNull
    private Matcher<RequestCriteria, Request> requestMatcher;

    @PostConstruct
    public void init() {
        hotSource = UnicastProcessor.create();
        hotFlux = hotSource.publish().autoConnect();
    }

    @EventListener
    public void onRatingUpdated(RatingUpdatedEvent event) {
        Request request = event.getRequest();
        log.info(RECEIVED_EVENT_MESSAGE, request.getId());
        hotSource.onNext(request);
    }

    @Override
    public Flux<Request> subscribeOn(RequestCriteria requestCriteria) {
        return hotFlux.filter(r -> requestMatcher.matches(requestCriteria, r));
    }
}
