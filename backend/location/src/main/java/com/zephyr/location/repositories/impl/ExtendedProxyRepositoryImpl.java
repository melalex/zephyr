package com.zephyr.location.repositories.impl;

import com.zephyr.data.criteria.ProxyCriteria;
import com.zephyr.data.enums.Protocol;
import com.zephyr.location.domain.Proxy;
import com.zephyr.location.repositories.ExtendedProxyRepository;
import lombok.Setter;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.ReactiveMongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Set;

@Repository
public class ExtendedProxyRepositoryImpl implements ExtendedProxyRepository {
    private static final String PROXY_COLLECTION_NAME = "Proxy";
    private static final String ISO_FIELD = "countryIso";
    private static final String PROTOCOL_FIELD = "protocol";

    @Setter(onMethod = @__(@Autowired))
    private ReactiveMongoOperations mongo;

    @Override
    public Flux<String> findProxiesLocations() {
        return Flux.from(mongo.getCollection(PROXY_COLLECTION_NAME).distinct(ISO_FIELD, String.class));
    }

    @Override
    public Flux<Proxy> findByCriteria(ProxyCriteria criteria, Pageable pageable) {
        return mongo.find(toQuery(criteria).with(pageable), Proxy.class);
    }

    @Override
    public Mono<Long> countByCriteria(ProxyCriteria criteria) {
        return mongo.count(toQuery(criteria), Proxy.class);
    }

    private Query toQuery(ProxyCriteria criteria) {
        Query query = Query.query(Criteria.where(ISO_FIELD).is(criteria.getCountryIso()));
        Set<Protocol> protocolsCriteria = criteria.getProtocols();

        if (CollectionUtils.isNotEmpty(protocolsCriteria)) {
            query = query.addCriteria(Criteria.where(PROTOCOL_FIELD).in(protocolsCriteria));
        }

        return query;
    }
}
