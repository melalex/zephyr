package com.zephyr.proxy.repositories.impl;

import com.google.common.base.Joiner;
import com.mongodb.client.result.UpdateResult;
import com.zephyr.data.enums.SearchEngine;
import com.zephyr.proxy.domain.Proxy;
import com.zephyr.proxy.properties.ProxyProperties;
import com.zephyr.proxy.repositories.ProxyOperations;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.ReactiveMongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
@RefreshScope
public class ProxyOperationsImpl implements ProxyOperations {
    private static final String ID_FIELD = "id";
    private static final String SCHEDULED_USAGE_FIELD = "scheduledUsage";
    private static final String FAILS_COUNT_FIELD = "failsCountField";
    private static final String FIELD_SEPARATOR = ".";

    @Setter(onMethod = @__(@Autowired))
    private ReactiveMongoOperations mongo;

    @Setter(onMethod = @__(@Autowired))
    private ProxyProperties proxyProperties;

    @Override
    public Mono<Proxy> findForReservation(SearchEngine engine) {
        String scheduledUsageField = getScheduledUsageField(engine);
        String failsCountField = getFailsCountField(engine);

        Query query = Query.query(Criteria.where(failsCountField).lt(proxyProperties.getMaxFailsCount()))
                .with(Sort.by(
                        Sort.Order.by(scheduledUsageField),
                        Sort.Order.by(failsCountField)
                ));

        return mongo.findOne(query, Proxy.class);
    }

    @Override
    public Mono<UpdateResult> report(String id, SearchEngine engine) {
        String scheduledUsageField = getScheduledUsageField(engine);
        String failsCountField = getFailsCountField(engine);

        Query query = Query.query(Criteria.where(ID_FIELD).is(id));

        Update update = new Update()
                .inc(failsCountField, 1)
                .inc(scheduledUsageField, proxyProperties.getEngineProperties(engine).getErrorDelay());

        return mongo.updateFirst(query, update, Proxy.class);
    }

    @Override
    public Mono<UpdateResult> resetFailedProxies() {
        Update update = new Update();

        for (SearchEngine engine : SearchEngine.values()) {
            update.set(getFailsCountField(engine), 0);
        }

        return mongo.update(Proxy.class)
                .apply(update)
                .all();
    }

    private String getScheduledUsageField(SearchEngine engine) {
        return Joiner.on(FIELD_SEPARATOR).join(SCHEDULED_USAGE_FIELD, engine);
    }

    private String getFailsCountField(SearchEngine engine) {
        return Joiner.on(FIELD_SEPARATOR).join(FAILS_COUNT_FIELD, engine);
    }
}