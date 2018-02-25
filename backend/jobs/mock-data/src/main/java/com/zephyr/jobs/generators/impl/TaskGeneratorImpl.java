package com.zephyr.jobs.generators.impl;

import com.zephyr.jobs.generators.TaskGenerator;
import com.zephyr.task.domain.Task;
import org.springframework.stereotype.Component;

@Component
public class TaskGeneratorImpl implements TaskGenerator {

    @Override
    public Task generate() {
        return null;
    }
}
