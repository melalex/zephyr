package com.zephyr.task.data;

import com.zephyr.task.domain.Task;
import com.zephyr.test.CommonTestData;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;

@AllArgsConstructor(access = AccessLevel.PACKAGE)
public final class TaskEntities {

    private ModelMapper modelMapper;

    public Task simple() {
        return modelMapper.map(CommonTestData.tasks().simple(), Task.class);
    }
}
