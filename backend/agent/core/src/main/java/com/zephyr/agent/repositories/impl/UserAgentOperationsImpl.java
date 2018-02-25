package com.zephyr.agent.repositories.impl;

import com.zephyr.agent.domain.UserAgent;
import com.zephyr.agent.repositories.UserAgentOperations;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.ReactiveMongoOperations;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.TypedAggregation;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
public class UserAgentOperationsImpl implements UserAgentOperations {
    private static final long AGGREGATION_SIZE = 1;

    @Setter(onMethod = @__(@Autowired))
    private ReactiveMongoOperations mongo;

    @Override
    public Mono<UserAgent> random() {
        TypedAggregation aggregation = TypedAggregation
                .newAggregation(UserAgent.class, Aggregation.sample(AGGREGATION_SIZE));
        return mongo.aggregate(aggregation, UserAgent.class)
                .collectList()
                .flatMap(l -> Mono.justOrEmpty(l.stream().findFirst()));
    }
}
