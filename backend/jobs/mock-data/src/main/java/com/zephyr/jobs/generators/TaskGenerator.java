package com.zephyr.jobs.generators;

import com.zephyr.task.domain.Task;

@FunctionalInterface
public interface TaskGenerator {

    Task generate();
}
