package com.zephyr.rating.services.handlers;

import com.zephyr.commons.ReactorUtils;
import com.zephyr.commons.interfaces.EventPublisher;
import com.zephyr.commons.interfaces.Handler;
import com.zephyr.rating.domain.Rating;
import com.zephyr.rating.domain.Request;
import com.zephyr.rating.repository.RatingRepository;
import com.zephyr.rating.repository.RequestRepository;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

@Component
public class RatingHandler implements Handler<List<Rating>> {

    @Setter(onMethod = @__(@Autowired))
    private RequestRepository requestRepository;

    @Setter(onMethod = @__(@Autowired))
    private RatingRepository ratingRepository;

    @Setter(onMethod = @__(@Autowired))
    private EventPublisher<Request> requestUpdatePublisher;

    @Override
    public void handle(List<Rating> target) {
        Mono.justOrEmpty(target.stream().findFirst())
                .flatMap(r -> findOrSave(r.getRequest()))
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
