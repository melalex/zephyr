package com.zephyr.jobs.batch.readers;

import static com.zephyr.jobs.batch.BatchConfiguration.CURRENT_INDEX;

import com.zephyr.jobs.generators.TaskGenerator;
import com.zephyr.task.domain.Task;
import lombok.Setter;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.ExecutionContext;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemStream;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.concurrent.atomic.AtomicInteger;

@Component
@StepScope
public class TaskReader implements ItemReader<Task>, ItemStream {

    @Setter(onMethod = @__(@Autowired))
    private TaskGenerator generator;

    @Setter(onMethod = @__(@Value("#{jobParameters[T(com.zephyr.jobs.batch.BatchConfiguration).TASK_COUNT_JOB_PARAM]}")))
    private int count;

    private AtomicInteger index;

    @Override
    public Task read() {
        int currentIndex = index.incrementAndGet();
        Task result = null;

        if (currentIndex <= count) {
            result = generator.generate();
        }

        return result;
    }

    @Override
    public void open(ExecutionContext executionContext) {
        if (executionContext.containsKey(CURRENT_INDEX)) {
            index = new AtomicInteger(executionContext.getInt(CURRENT_INDEX));
        } else {
            index = new AtomicInteger();
        }
    }

    @Override
    public void update(ExecutionContext executionContext) {
        executionContext.putInt(CURRENT_INDEX, index.intValue());
    }

    @Override
    public void close() {

    }
}
