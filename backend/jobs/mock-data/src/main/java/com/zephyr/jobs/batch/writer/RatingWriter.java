package com.zephyr.jobs.batch.writer;

import com.zephyr.jobs.generators.RatingGenerator;
import com.zephyr.jobs.generators.RequestGenerator;
import com.zephyr.rating.domain.Request;
import com.zephyr.task.domain.Task;
import lombok.Setter;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Stream;

@Component
public class RatingWriter implements ItemWriter<Task> {

    @Setter(onMethod = @__(@Autowired))
    private RequestGenerator requestGenerator;

    @Setter(onMethod = @__(@Autowired))
    private RatingGenerator ratingGenerator;

    @Setter(onMethod = @__(@Autowired))
    private MongoOperations mongo;

    @Override
    public void write(List<? extends Task> items) {
        items.stream()
                .flatMap(convertAndSaveRequest())
                .map(ratingGenerator::generate)
                .forEach(mongo::save);
    }

    private Function<Task, Stream<Request>> convertAndSaveRequest() {
        return task -> task.getSearchCriteria().stream()
                .flatMap(s -> requestGenerator.generate(s).stream())
                .peek(mongo::save);
    }
}
