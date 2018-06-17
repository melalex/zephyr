package com.zephyr.rating.repository.impl;

import com.zephyr.rating.domain.Rating;
import com.zephyr.rating.domain.RequestCriteria;
import com.zephyr.rating.repository.RatingOperations;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

import java.util.Optional;

@Repository
@AllArgsConstructor
public class RatingOperationsImpl implements RatingOperations {

    private ReactiveMongoTemplate mongo;

    @Override
    public Flux<String> findAllByUrlStartsWith(String url, Pageable pageable) {
        Query query = Query.query(Criteria.where(Rating.URL_FIELD).regex(url))
                .with(pageable);

        return Flux.from(mongo.getCollection(mongo.getCollectionName(Rating.class))
                .distinct(Rating.QUERY_STRING_FIELD, query.getQueryObject(), String.class));
    }

    @Override
    public Flux<Rating> findByCriteria(RequestCriteria requestCriteria) {
        Criteria criteria = Criteria.where(Rating.QUERY_FIELD).is(requestCriteria.getQueryCriteria())
                .and(Rating.URL_FIELD).regex(requestCriteria.getUrl())
                .and(Rating.PROVIDER_FIELD).is(requestCriteria.getEngine());

        Optional.ofNullable(requestCriteria.getFrom()).ifPresent(f -> criteria.and(Rating.TIMESTAMP_FIELD).gte(f));
        Optional.ofNullable(requestCriteria.getTo()).ifPresent(t -> criteria.and(Rating.TIMESTAMP_FIELD).lte(t));

        return mongo.find(Query.query(criteria), Rating.class);
    }
}
