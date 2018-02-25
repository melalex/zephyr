package com.zephyr.jobs.batch;

import com.zephyr.rating.domain.Rating;
import com.zephyr.task.domain.Task;
import lombok.Setter;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.StepExecutionListener;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BatchConfiguration {
    private static final String LOAD_MOCK_DATA_JOB_NAME = "loadMockDataJob";
    private static final String LOAD_MOCK_DATA_STEP_NAME = "loadMockDataStep";

    private static final int BATCH_SIZE = 50;

    @Setter(onMethod = @__(@Autowired))
    public JobBuilderFactory jobs;

    @Setter(onMethod = @__(@Autowired))
    public StepBuilderFactory steps;

    @Bean
    public Job loadMockDataJob(Step loadMockDataStep) {
        return jobs.get(LOAD_MOCK_DATA_JOB_NAME)
                .start(loadMockDataStep)
                .build();
    }

    @Bean
    public Step loadMockDataStep(ItemReader<Task> taskReader, StepExecutionListener mockDataStepListener) {
        return steps.get(LOAD_MOCK_DATA_STEP_NAME)
                .listener(mockDataStepListener)
                .<Task, Task>chunk(BATCH_SIZE)
                .reader(taskReader)
                .writer(compositeTaskWriter())
                .build();
    }

    @Bean
    public ItemWriter<Task> compositeTaskWriter() {
        return null;
    }

    @Bean
    public ItemWriter<Rating> compositeRatingWriter() {
        return null;
    }
}
