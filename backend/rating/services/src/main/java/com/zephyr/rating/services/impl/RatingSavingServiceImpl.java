package com.zephyr.rating.services.impl;

import com.zephyr.commons.ReactorUtils;
import com.zephyr.commons.interfaces.EventPublisher;
import com.zephyr.commons.support.Indexed;
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
import reactor.util.function.Tuple2;

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
    public void save(Tuple2<Request, List<Indexed<String>>> target) {
        Mono.just(target)
                .transform(ReactorUtils.flatMapFirst(this::findOrSave))
                .flatMap(this::saveRating)
                .subscribe(requestUpdatePublisher::publish);
    }

    private Mono<Request> findOrSave(Request request) {
        return requestRepository.findOne(Example.of(request))
                .switchIfEmpty(requestRepository.save(request));
    }

    private Mono<Request> saveRating(Tuple2<Request, List<Indexed<String>>> target) {
        return Flux.fromIterable(target.getT2())
                .map(r -> new Rating(target.getT1(), r.getIndex(), r.getElement()))
                .flatMap(ratingRepository::save)
                .then(Mono.just(target.getT1()));
    }
}
