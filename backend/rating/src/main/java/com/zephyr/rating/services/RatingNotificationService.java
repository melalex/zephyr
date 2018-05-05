package com.zephyr.rating.services;

import com.zephyr.rating.domain.Request;

public interface RatingNotificationService {

    void publishRatingUpdatedEvent(Request request);
}
