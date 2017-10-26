package com.zephyr.proxy.repositories.impl;

import com.zephyr.data.enums.SearchEngine;
import com.zephyr.proxy.domain.Proxy;
import com.zephyr.proxy.repositories.ProxyOperations;
import lombok.Setter;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.ReactiveMongoOperations;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
public class ProxyOperationsImpl implements ProxyOperations {

    @Setter(onMethod = @__(@Autowired))
    private ReactiveMongoOperations mongo;

    @Value("${}")
    private long delay;

    @Override
    public Mono<Proxy> reserve(SearchEngine engine) {
        String scheduledUsageField = "scheduledUsage." + engine;
        String failsCountField = "failsCount." + engine;

        Query query = new Query()
                .limit(1)
                .with(Sort.by(
                        Sort.Order.by(scheduledUsageField),
                        Sort.Order.desc(failsCountField)
                ));

        Update update = new Update()
                .inc(scheduledUsageField, 1)
                .isolated();

        return mongo.findAndModify(query, update, Proxy.class);
    }
}