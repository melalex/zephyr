package com.zephyr.task.data;

import com.zephyr.test.CommonTestData;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class TaskTestData {

    private static final TaskTestData INSTANCE = new TaskTestData();

    private CriteriaEntities criteriaEntities =
            new CriteriaEntities(CommonTestData.places(), CommonTestData.userAgents());

    private TaskEntities taskEntities = new TaskEntities(criteriaEntities);


    public static CriteriaEntities criteria() {
        return INSTANCE.criteriaEntities;
    }

    public static TaskEntities tasks() {
        return INSTANCE.taskEntities;
    }
}
