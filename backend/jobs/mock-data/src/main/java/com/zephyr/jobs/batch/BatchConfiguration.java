package com.zephyr.jobs.batch;

import com.zephyr.task.domain.Task;
import lombok.Setter;
import lombok.SneakyThrows;
import me.tongfei.progressbar.ProgressBar;
import me.tongfei.progressbar.ProgressBarStyle;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.StepExecutionListener;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.support.CompositeItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.SimpleAsyncTaskExecutor;
import org.springframework.core.task.TaskExecutor;

import java.util.List;

@Configuration
public class BatchConfiguration {

    public static final String CURRENT_INDEX = "current.index";
    public static final String TASK_COUNT_JOB_PARAM = "task.count";
    public static final String CRITERIA_PER_TASK_JOB_PARAM = "criteria.count";
    public static final String REQUEST_PER_CRITERIA_JOB_PARAM = "request.count";
    public static final String RATING_PER_REQUEST_JOB_PARAM = "rating.count";

    private static final String LOAD_MOCK_DATA_JOB_NAME = "loadMockDataJob";
    private static final String LOAD_MOCK_DATA_STEP_NAME = "loadMockDataStep";
    private static final String LOAD_MOCK_DATA_TASK = "Load mock data";

    @Setter(onMethod = @__(@Autowired))
    public JobBuilderFactory jobs;

    @Setter(onMethod = @__(@Autowired))
    public StepBuilderFactory steps;

    @Setter(onMethod = @__(@Autowired))
    public ItemReader<Task> taskReader;

    @Setter(onMethod = @__(@Autowired))
    public StepExecutionListener mockDataStepListener;

    @Bean
    public Job loadMockDataJob(Step loadMockDataStep) {
        return jobs.get(LOAD_MOCK_DATA_JOB_NAME)
                .start(loadMockDataStep)
                .build();
    }

    @Bean
    public Step loadMockDataStep(ItemWriter<Task> compositeTaskWriter, @Value("${data.batchSize}") int batchSize) {
        return steps.get(LOAD_MOCK_DATA_STEP_NAME)
                .listener(mockDataStepListener)
                .<Task, Task>chunk(batchSize)
                .reader(taskReader)
                .writer(compositeTaskWriter)
                .taskExecutor(taskExecutor())
                .build();
    }

    @Bean
    @SneakyThrows
    public ItemWriter<Task> compositeTaskWriter(List<ItemWriter<? super Task>> writers) {
        CompositeItemWriter<Task> writer = new CompositeItemWriter<>();
        writer.setDelegates(writers);
        writer.afterPropertiesSet();
        return writer;
    }

    @Bean
    public TaskExecutor taskExecutor() {
        return new SimpleAsyncTaskExecutor();
    }

    @Bean
    @StepScope
    public ProgressBar progressBar(@Value("#{jobParameters[T(com.zephyr.jobs.batch.BatchConfiguration).TASK_COUNT_JOB_PARAM]}") int taskCount) {
        return new ProgressBar(LOAD_MOCK_DATA_TASK, taskCount, ProgressBarStyle.ASCII);
    }
}
