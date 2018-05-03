package com.zephyr.task.data;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class TaskTestData {

    private static final TaskTestData INSTANCE = createInstance();

    private TaskEntities taskEntities;

    private static TaskTestData createInstance() {
        TaskTestData instance = new TaskTestData();
        ModelMapper modelMapper = new ModelMapper();
        instance.taskEntities = new TaskEntities(modelMapper);

        return instance;
    }

    public static TaskEntities tasks() {
        return INSTANCE.taskEntities;
    }
}
