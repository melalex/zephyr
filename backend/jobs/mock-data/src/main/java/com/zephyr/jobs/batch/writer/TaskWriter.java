package com.zephyr.jobs.batch.writer;

import com.zephyr.jobs.generators.MeteredSearchCriteriaGenerator;
import com.zephyr.task.domain.Task;
import lombok.Setter;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.function.Consumer;

@Component
public class TaskWriter implements ItemWriter<Task> {

    @Setter(onMethod = @__(@Autowired))
    private MongoOperations mongo;

    @Setter(onMethod = @__(@Autowired))
    private MeteredSearchCriteriaGenerator generator;

    @Override
    public void write(List<? extends Task> items) {
        items.stream()
                .peek(saveCriteria())
                .forEach(mongo::save);
    }

    private Consumer<Task> saveCriteria() {
        return t -> t.getSearchCriteria().stream()
                .peek(mongo::save)
                .map(generator::generate)
                .forEach(mongo::save);
    }
}
