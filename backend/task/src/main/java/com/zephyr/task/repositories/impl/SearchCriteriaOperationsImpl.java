package com.zephyr.task.repositories.impl;

import static org.springframework.data.mongodb.core.query.Criteria.byExample;
import static org.springframework.data.mongodb.core.query.Criteria.where;
import static org.springframework.data.mongodb.core.query.Query.query;

import com.zephyr.commons.interfaces.UidProvider;
import com.zephyr.task.domain.SearchCriteria;
import com.zephyr.task.repositories.SearchCriteriaOperations;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.ReactiveMongoOperations;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Clock;
import java.time.LocalDateTime;
import java.time.temporal.TemporalAmount;

@Repository
@AllArgsConstructor
public class SearchCriteriaOperationsImpl implements SearchCriteriaOperations {

    private static final Number INC_VALUE = 1;

    private ReactiveMongoOperations mongo;
    private UidProvider uidProvider;
    private Clock clock;

    @Override
    public Mono<SearchCriteria> updateUsage(SearchCriteria searchCriteria) {
        Query query = query(byExample(Example.of(searchCriteria)));

        Update update = new Update()
                .inc(SearchCriteria.HITS_COUNT_FIELD, INC_VALUE)
                .set(SearchCriteria.LAST_HIT_FIELD, LocalDateTime.now(clock))
                .isolated();

        return mongo.findAndModify(query, update, SearchCriteria.class);
    }

    @Override
    public Flux<SearchCriteria> findAllForUpdate(TemporalAmount relevancePeriod, Pageable pageable) {
        String transactionId = uidProvider.provide();

        Query queryUpdate =
                query(where(SearchCriteria.LAST_UPDATE_FIELD).lt(LocalDateTime.now(clock).minus(relevancePeriod)))
                        .with(pageable);

        Query queryResult = query(where(SearchCriteria.TRANSACTION_ID_FIELD).is(transactionId))
                .with(pageable);

        Update update = new Update()
                .currentTimestamp(SearchCriteria.LAST_UPDATE_FIELD)
                .set(SearchCriteria.TRANSACTION_ID_FIELD, transactionId);

        return mongo.updateMulti(queryUpdate, update, SearchCriteria.class)
                .thenMany(mongo.find(queryResult, SearchCriteria.class));
    }
}
