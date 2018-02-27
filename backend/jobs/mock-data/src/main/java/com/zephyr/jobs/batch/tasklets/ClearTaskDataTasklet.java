package com.zephyr.jobs.batch.tasklets;

import com.zephyr.task.domain.SearchCriteria;
import com.zephyr.task.domain.Task;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.stereotype.Component;

@Component
public class ClearTaskDataTasklet extends AbstractClearTasklet {

    @Setter(onMethod = @__(@Autowired))
    private MongoOperations mongo;

    @Override
    protected void clear() {
        mongo.dropCollection(Task.class);
        mongo.dropCollection(SearchCriteria.class);
    }
}
