package com.zephyr.task.repositories.impl;

import static org.springframework.data.mongodb.core.query.Criteria.where;
import static org.springframework.data.mongodb.core.query.Query.query;

import com.zephyr.task.domain.Task;
import com.zephyr.task.repositories.TaskOperations;
import lombok.AllArgsConstructor;
import org.springframework.data.mongodb.core.ReactiveMongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
@AllArgsConstructor
public class TaskOperationsImpl implements TaskOperations {

    private ReactiveMongoOperations mongo;

    @Override
    public Mono<Task> findByUserIdAndIdOrShared(String userId, String id) {
        Criteria criteria = where(Task.ID_FIELD).is(id)
                .orOperator(where(Task.USER_ID_FIELD).is(userId), where(Task.SHARED_FIELD).is(true));

        return mongo.findOne(query(criteria), Task.class);
    }
}
