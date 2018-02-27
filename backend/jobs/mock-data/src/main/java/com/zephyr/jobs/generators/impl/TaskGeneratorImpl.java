package com.zephyr.jobs.generators.impl;

import com.zephyr.jobs.generators.TaskGenerator;
import com.zephyr.task.domain.Task;
import lombok.Setter;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@StepScope
public class TaskGeneratorImpl implements TaskGenerator {

    @Setter(onMethod = @__(@Value("#{jobParameters[T(com.zephyr.jobs.batch.BatchConfiguration).CRITERIA_PER_TASK_JOB_PARAM]}")))
    private int criteriaPerTask;

    @Override
    public Task generate() {
        return null;
    }
}
