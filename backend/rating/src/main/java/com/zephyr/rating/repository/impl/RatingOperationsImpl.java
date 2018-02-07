package com.zephyr.rating.repository.impl;

import com.zephyr.rating.domain.Rating;
import com.zephyr.rating.domain.RatingCriteria;
import com.zephyr.rating.repository.RatingOperations;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.ReactiveMongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

@Repository
public class RatingOperationsImpl implements RatingOperations {
    private static final String COLLECTION_NAME = "Rating";
    private static final String TIMESTAMP_FIELD = "timestamp";
    private static final String PROVIDER_FIELD = "provider";
    private static final String URL_FIELD = "url";
    private static final String QUERY_FIELD = "query.query";

    @Setter(onMethod = @__(@Autowired))
    private ReactiveMongoOperations mongo;

    @Override
    public Flux<Rating> findByUrl(String url, Pageable pageable) {
        Query query = Query.query(Criteria.where(URL_FIELD).is(url))
                .with(pageable);

        return Flux.from(mongo.getCollection(COLLECTION_NAME)
                .distinct(QUERY_FIELD, query.getQueryObject(), Rating.class));
    }

    @Override
    public Flux<Rating> findByCriteria(RatingCriteria ratingCriteria) {
        Criteria criteria = Criteria.byExample(new Rating(ratingCriteria.getUrl(), ratingCriteria.getQuery()))
                .and(TIMESTAMP_FIELD).gte(ratingCriteria.getFrom())
                .and(TIMESTAMP_FIELD).lte(ratingCriteria.getTo())
                .and(PROVIDER_FIELD).in(ratingCriteria.getEngines());

        return mongo.find(Query.query(criteria), Rating.class);
    }
}
