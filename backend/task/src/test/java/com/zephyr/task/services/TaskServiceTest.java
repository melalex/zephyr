package com.zephyr.task.services;

import com.zephyr.errors.exceptions.ResourceNotFoundException;
import com.zephyr.task.TaskTestConfiguration;
import com.zephyr.task.data.TaskTestData;
import com.zephyr.task.domain.Task;
import com.zephyr.task.repositories.TaskRepository;
import com.zephyr.test.mocks.PrincipalMock;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit4.SpringRunner;
import reactor.test.StepVerifier;

@SpringBootTest
@RunWith(SpringRunner.class)
public class TaskServiceTest {

    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private TaskService testInstance;

    private Task target;

    @Before
    public void setUp() {
        target = TaskTestData.tasks().simple();
        taskRepository.save(target).subscribe();
    }

    @After
    public void tearDown() {
        taskRepository.deleteAll().subscribe();
    }

    @Test
    public void shouldNotRemoveIfNotOwner() {
        StepVerifier.create(testInstance.remove(target.getId(), PrincipalMock.user1()))
                .expectError(ResourceNotFoundException.class)
                .verify();
    }

    @TestConfiguration
    @Import(TaskTestConfiguration.class)
    public static class Configuration {

    }
}