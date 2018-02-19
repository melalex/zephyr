package com.zephyr.rating.services.impl;

import com.zephyr.commons.ReactorUtils;
import com.zephyr.commons.interfaces.EventPublisher;
import com.zephyr.rating.domain.Rating;
import com.zephyr.rating.domain.Request;
import com.zephyr.rating.repository.RatingRepository;
import com.zephyr.rating.repository.RequestRepository;
import com.zephyr.rating.services.RatingSavingService;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

@Service
public class RatingSavingServiceImpl implements RatingSavingService {

    @Setter(onMethod = @__(@Autowired))
    private RequestRepository requestRepository;

    @Setter(onMethod = @__(@Autowired))
    private RatingRepository ratingRepository;

    @Setter(onMethod = @__(@Autowired))
    private EventPublisher<Request> requestUpdatePublisher;

    @Override
    @ServiceActivator
    public void save(List<Rating> target) {
        Mono.justOrEmpty(target.stream().findFirst())
                .map(Rating::getRequest)
                .flatMap(this::findOrSave)
                .transform(ReactorUtils.doOnNextAsync(r -> save(target, r)))
                .subscribe(requestUpdatePublisher::publish);
    }

    private Mono<Request> findOrSave(Request request) {
        return requestRepository.findOne(Example.of(request))
                .switchIfEmpty(requestRepository.save(request));
    }

    private Flux<Rating> save(List<Rating> ratings, Request request) {
        return Flux.fromIterable(ratings)
                .doOnNext(t -> t.setRequest(request))
                .flatMap(ratingRepository::save);
    }
}
