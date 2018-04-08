package com.zephyr.rating.core.repository.impl;

import com.zephyr.rating.core.domain.Rating;
import com.zephyr.rating.core.domain.RequestCriteria;
import com.zephyr.rating.core.repository.RatingOperations;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.ReactiveMongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

@Repository
public class RatingOperationsImpl implements RatingOperations {

    private static final String COLLECTION_NAME = "Rating";
    private static final String URL_FIELD = "url";
    private static final String QUERY_FIELD = "request.query";
    private static final String TIMESTAMP_FIELD = "request.timestamp";
    private static final String QUERY_STRING_FIELD = "request.query.query";
    private static final String PROVIDER_FIELD = "request.provider";

    @Setter(onMethod = @__(@Autowired))
    private ReactiveMongoOperations mongo;

    @Override
    public Flux<Rating> findAllByUrl(String url, Pageable pageable) {
        Query query = Query.query(Criteria.where(URL_FIELD).is(url))
                .with(pageable);

        return Flux.from(mongo.getCollection(COLLECTION_NAME)
                .distinct(QUERY_STRING_FIELD, query.getQueryObject(), Rating.class));
    }

    @Override
    public Flux<Rating> findByCriteria(RequestCriteria requestCriteria) {
        Criteria criteria = Criteria.where(URL_FIELD).is(requestCriteria.getUrl())
                .and(QUERY_FIELD).alike(Example.of(requestCriteria.getQuery()))
                .and(TIMESTAMP_FIELD).gte(requestCriteria.getFrom())
                .and(TIMESTAMP_FIELD).lte(requestCriteria.getTo())
                .and(PROVIDER_FIELD).in(requestCriteria.getEngines());

        return mongo.find(Query.query(criteria), Rating.class);
    }
}
