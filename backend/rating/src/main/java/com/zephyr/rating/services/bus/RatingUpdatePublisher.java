package com.zephyr.rating.services.bus;

import com.zephyr.rating.domain.Query;

public interface RatingUpdatePublisher {

    void publish(Query query);
}
