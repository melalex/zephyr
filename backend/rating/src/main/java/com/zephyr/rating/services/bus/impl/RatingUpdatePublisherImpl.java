package com.zephyr.rating.services.bus.impl;

import com.zephyr.rating.domain.Query;
import com.zephyr.rating.services.bus.RatingUpdatePublisher;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.Topic;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class RatingUpdatePublisherImpl implements RatingUpdatePublisher {
    private static final String PUBLISH_QUERY_MESSAGE = "Publishing Query: {}";

    @Setter(onMethod = @__(@Autowired))
    private RedisTemplate<String, Query> template;

    @Setter(onMethod = @__(@Autowired))
    private Topic queriesTopic;

    @Override
    public void publish(Query query) {
        log.info(PUBLISH_QUERY_MESSAGE, query);
        template.convertAndSend(queriesTopic.getTopic(), query);
    }
}
