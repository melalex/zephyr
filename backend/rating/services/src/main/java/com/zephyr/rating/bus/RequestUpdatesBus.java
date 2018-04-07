package com.zephyr.rating.bus;

import com.zephyr.commons.interfaces.Bus;
import com.zephyr.commons.interfaces.Matcher;
import com.zephyr.rating.domain.Request;
import com.zephyr.rating.domain.RequestCriteria;
import com.zephyr.rating.events.RatingUpdatedEvent;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.UnicastProcessor;

import javax.annotation.PostConstruct;

@Slf4j
@Component
public class RequestUpdatesBus implements Bus<RequestCriteria, Request> {

    private static final String RECEIVED_EVENT_MESSAGE = "Received new Rating: {}";

    private UnicastProcessor<Request> hotSource;
    private Flux<Request> hotFlux;

    @Setter(onMethod = @__(@Autowired))
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
    public Flux<Request> updatesFor(RequestCriteria requestCriteria) {
        return hotFlux.filter(r -> requestMatcher.matches(requestCriteria, r));
    }
}
