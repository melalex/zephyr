package com.zephyr.jobs.batch.tasklets;

import com.zephyr.rating.domain.Rating;
import com.zephyr.rating.domain.Request;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.stereotype.Component;

@Component
public class ClearRatingDataTasklet extends AbstractClearTasklet {

    @Setter(onMethod = @__(@Autowired))
    private MongoOperations mongo;

    @Override
    protected void clear() {
        mongo.dropCollection(Rating.class);
        mongo.dropCollection(Request.class);
    }
}
