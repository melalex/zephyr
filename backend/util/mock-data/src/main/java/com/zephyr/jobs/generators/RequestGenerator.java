package com.zephyr.jobs.generators;

import com.zephyr.rating.domain.Request;
import com.zephyr.task.domain.Task;

import java.util.List;

public interface RequestGenerator {

    List<Request> generate(Task task);
}
