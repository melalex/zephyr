package com.zephyr.task.controllers;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.reactive.server.WebTestClient;

@SpringBootTest
@AutoConfigureWebTestClient
@RunWith(SpringRunner.class)
public class TaskControllerIntegrationTest {

    @Autowired
    private WebTestClient webTestClient;

    @Before
    public void setUp() {

    }

    @Test
    public void shouldCreateTask() {

    }

    @Test
    public void shouldCreateTaskForNewCriteria() {

    }

    @Test
    public void shouldFindAll() {

    }

    @Test
    public void shouldFindById() {

    }

    @Test
    public void shouldRemove() {

    }
}