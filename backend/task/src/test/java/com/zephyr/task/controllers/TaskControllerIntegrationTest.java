package com.zephyr.task.controllers;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import com.zephyr.data.protocol.dto.TaskDto;
import com.zephyr.task.TaskTestConfiguration;
import com.zephyr.task.data.TaskTestData;
import com.zephyr.task.domain.SearchCriteria;
import com.zephyr.task.domain.Task;
import com.zephyr.task.util.WebClientUtil;
import com.zephyr.test.CommonTestData;
import com.zephyr.test.Tasks;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;

import java.util.List;

@SpringBootTest
@AutoConfigureWebTestClient
@RunWith(SpringRunner.class)
@Import(TaskTestConfiguration.class)
public class TaskControllerIntegrationTest {

    @Autowired
    private TaskController taskController;

    @Autowired
    private MongoOperations mongoOperations;

    private WebTestClient webTestClient;

    @Before
    public void setUp() {
        webTestClient = WebTestClient.bindToController(taskController).build();
        Task savedTask = TaskTestData.tasks().simple();
        SearchCriteria savedSearchCriteria = savedTask.getSearchCriteria().get(0);
        mongoOperations.save(savedSearchCriteria);
        mongoOperations.save(savedTask);
    }

    @After
    public void tearDown() {
        mongoOperations.dropCollection(Task.class);
    }

    @Test
    @WithMockUser(username = Tasks.SIMPLE_USER_ID)
    public void shouldCreateFindAndRemoveTask() {
//        @formatter:off
        TaskDto actual = webTestClient.post()
                .body(Mono.just(CommonTestData.tasks().withNewCriteria()), TaskDto.class)
                .exchange()
                    .expectStatus()
                        .isCreated()
                    .expectBody(TaskDto.class)
                        .returnResult()
                        .getResponseBody();
//        @formatter:on

        assertNotNull(actual);

        String id = actual.getId();

//        @formatter:off
        TaskDto expected = webTestClient.get()
                .uri(WebClientUtil.from(id))
                .exchange()
                    .expectStatus()
                        .isOk()
                    .expectBody(TaskDto.class)
                        .returnResult()
                        .getResponseBody();
//        @formatter:on

        assertEquals(expected, actual);

//        @formatter:off

        webTestClient.delete()
                .uri(WebClientUtil.from(id))
                .exchange()
                    .expectStatus()
                        .isNoContent();

        webTestClient.get()
                .uri(WebClientUtil.from(id))
                .exchange()
                    .expectStatus()
                        .isNotFound();
//        @formatter:on
    }

    @Test
    public void shouldFindAll() {
//        @formatter:off
        List<TaskDto> actual = webTestClient.get()
                .exchange()
                    .expectStatus()
                        .isOk()
                    .expectBodyList(TaskDto.class)
                        .returnResult()
                        .getResponseBody();
//        @formatter:on

        assertEquals(List.of(CommonTestData.tasks().simple()), actual);
    }
}