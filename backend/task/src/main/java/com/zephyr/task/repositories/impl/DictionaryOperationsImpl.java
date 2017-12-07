package com.zephyr.task.repositories.impl;

import com.zephyr.data.commons.Keyword;
import com.zephyr.task.domain.Dictionary;
import com.zephyr.task.domain.factories.DictionaryFactory;
import com.zephyr.task.repositories.DictionaryOperations;
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
public class DictionaryOperationsImpl implements DictionaryOperations {
    private static final String HITS_COUNT_FIELD = "hitsCount";
    private static final String LAST_HIT_FIELD = "lastHit";
    private static final String LAST_UPDATE_FIELD = "lastUpdate";
    private static final String TRANSACTION_ID_FIELD = "transactionId";
    private static final Number INC_VALUE = 1;

    @Setter(onMethod = @__(@Autowired))
    private ReactiveMongoOperations mongo;

    @Setter(onMethod = @__(@Autowired))
    private DictionaryFactory dictionaryFactory;

    @Override
    public Mono<Dictionary> updateUsage(Keyword keyword) {
        Query query = Query.query(Criteria.byExample(dictionaryFactory.newDictionaryExample(keyword)));

        Update update = new Update()
                .inc(HITS_COUNT_FIELD, INC_VALUE)
                .currentTimestamp(LAST_HIT_FIELD)
                .isolated();

        return mongo.findAndModify(query, update, Dictionary.class);
    }

    @Override
    public Flux<Dictionary> findAllForUpdate(ReadablePeriod relevancePeriod, Pageable pageable) {
        String transactionId = createTransactionId();

        Query queryUpdate = Query.query(Criteria.where(LAST_UPDATE_FIELD).lt(DateTime.now().minus(relevancePeriod)))
                .with(pageable);

        Query queryResult = Query.query(Criteria.where(TRANSACTION_ID_FIELD).is(transactionId))
                .with(pageable);

        Update update = new Update()
                .currentTimestamp(LAST_UPDATE_FIELD)
                .set(TRANSACTION_ID_FIELD, transactionId);

        return mongo.updateMulti(queryUpdate, update, Dictionary.class)
                .thenMany(mongo.find(queryResult, Dictionary.class));
    }

    private String createTransactionId() {
        return UUID.randomUUID().toString();
    }
}