package com.zephyr.rating.services;

import com.zephyr.rating.domain.Request;
import com.zephyr.rating.domain.RequestCriteria;
import reactor.core.publisher.Flux;

public interface SubscriptionService {

    Flux<Request> subscribeOn(RequestCriteria requestCriteria);
}
