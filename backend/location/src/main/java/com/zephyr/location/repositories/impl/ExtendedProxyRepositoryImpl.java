package com.zephyr.location.repositories.impl;

import com.zephyr.location.repositories.ExtendedProxyRepository;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.ReactiveMongoOperations;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

@Repository
public class ExtendedProxyRepositoryImpl implements ExtendedProxyRepository {
    private static final String PROXY_COLLECTION_NAME = "Proxy";
    private static final String ISO_FIELD = "countryIso";

    @Setter(onMethod = @__(@Autowired))
    private ReactiveMongoOperations mongo;

    @Override
    public Flux<String> findProxiesLocations() {
        return Flux.from(mongo.getCollection(PROXY_COLLECTION_NAME).distinct(ISO_FIELD, String.class));
    }
}
