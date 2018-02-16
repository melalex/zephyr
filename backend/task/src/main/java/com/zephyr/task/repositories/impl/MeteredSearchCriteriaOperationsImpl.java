package com.zephyr.task.repositories.impl;

import com.zephyr.task.domain.SearchCriteria;
import com.zephyr.task.domain.MeteredSearchCriteria;
import com.zephyr.task.domain.factories.MeteredSearchCriteriaFactory;
import com.zephyr.task.repositories.MeteredSearchCriteriaOperations;
import lombok.Setter;
import org.joda.time.DateTime;
import org.joda.time.ReadablePeriod;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.ReactiveMongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Repository
public class MeteredSearchCriteriaOperationsImpl implements MeteredSearchCriteriaOperations {
    private static final String HITS_COUNT_FIELD = "hitsCount";
    private static final String LAST_HIT_FIELD = "lastHit";
    private static final String LAST_UPDATE_FIELD = "lastUpdate";
    private static final String TRANSACTION_ID_FIELD = "transactionId";
    private static final Number INC_VALUE = 1;

    @Setter(onMethod = @__(@Autowired))
    private ReactiveMongoOperations mongo;

    @Setter(onMethod = @__(@Autowired))
    private MeteredSearchCriteriaFactory meteredSearchCriteriaFactory;

    @Override
    public Mono<MeteredSearchCriteria> updateUsage(SearchCriteria searchCriteria) {
        Query query = Query.query(Criteria.byExample(meteredSearchCriteriaFactory.createExample(searchCriteria)));

        Update update = new Update()
                .inc(HITS_COUNT_FIELD, INC_VALUE)
                .currentDate(LAST_HIT_FIELD)
                .isolated();

        return mongo.findAndModify(query, update, MeteredSearchCriteria.class);
    }

    @Override
    public Flux<MeteredSearchCriteria> findAllForUpdate(ReadablePeriod relevancePeriod, Pageable pageable) {
        String transactionId = createTransactionId();

        Query queryUpdate = Query.query(Criteria.where(LAST_UPDATE_FIELD).lt(DateTime.now().minus(relevancePeriod)))
                .with(pageable);

        Query queryResult = Query.query(Criteria.where(TRANSACTION_ID_FIELD).is(transactionId))
                .with(pageable);

        Update update = new Update()
                .currentTimestamp(LAST_UPDATE_FIELD)
                .set(TRANSACTION_ID_FIELD, transactionId);

        return mongo.updateMulti(queryUpdate, update, MeteredSearchCriteria.class)
                .thenMany(mongo.find(queryResult, MeteredSearchCriteria.class));
    }

    private String createTransactionId() {
        return UUID.randomUUID().toString();
    }
}
