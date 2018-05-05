package com.zephyr.rating.repository.impl;

import com.zephyr.rating.domain.Rating;
import com.zephyr.rating.domain.RequestCriteria;
import com.zephyr.rating.repository.RatingOperations;
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

    @Setter(onMethod = @__(@Autowired))
    private ReactiveMongoOperations mongo;

    @Override
    public Flux<Rating> findAllByUrlStartsWith(String url, Pageable pageable) {
        Query query = Query.query(Criteria.where(Rating.URL_FIELD).regex(url))
                .with(pageable);

        return Flux.from(mongo.getCollection(Rating.COLLECTION_NAME)
                .distinct(Rating.QUERY_STRING_FIELD, query.getQueryObject(), Rating.class));
    }

    @Override
    public Flux<Rating> findByCriteria(RequestCriteria requestCriteria) {
        Criteria criteria = Criteria.where(Rating.URL_FIELD).is(requestCriteria.getUrl())
                .and(Rating.QUERY_FIELD).alike(Example.of(requestCriteria.getQuery()))
                .and(Rating.TIMESTAMP_FIELD).gte(requestCriteria.getFrom())
                .and(Rating.TIMESTAMP_FIELD).lte(requestCriteria.getTo())
                .and(Rating.PROVIDER_FIELD).in(requestCriteria.getEngines());

        return mongo.find(Query.query(criteria), Rating.class);
    }
}
