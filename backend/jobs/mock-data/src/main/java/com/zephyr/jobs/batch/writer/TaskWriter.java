package com.zephyr.jobs.batch.writer;

import com.zephyr.task.domain.Task;
import com.zephyr.task.repositories.TaskRepository;
import lombok.Setter;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class TaskWriter implements ItemWriter<Task> {

    @Setter(onMethod = @__(@Autowired))
    private MongoOperations mongo;

    @Override
    public void write(List<? extends Task> items) {
        items.stream()
                .peek(t -> t.getSearchCriteria().forEach(mongo::save))
                .forEach(mongo::save);
    }
}
