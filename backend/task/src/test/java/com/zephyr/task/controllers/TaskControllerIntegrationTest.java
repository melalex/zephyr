package com.zephyr.task.controllers;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.springframework.security.test.web.reactive.server.SecurityMockServerConfigurers.springSecurity;
import static org.springframework.web.reactive.function.client.ExchangeFilterFunctions.basicAuthentication;

import com.zephyr.commons.support.Profiles;
import com.zephyr.data.protocol.dto.TaskDto;
import com.zephyr.task.TaskApplication;
import com.zephyr.task.TaskTestConfiguration;
import com.zephyr.task.data.TaskTestData;
import com.zephyr.task.domain.Task;
import com.zephyr.test.CommonTestData;
import com.zephyr.test.Tasks;
import com.zephyr.test.mocks.PrincipalMock;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.http.HttpHeaders;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.util.List;

@RunWith(SpringRunner.class)
@ActiveProfiles(Profiles.TEST)
@ContextConfiguration(classes = {TaskApplication.class, TaskTestConfiguration.class})
public class TaskControllerIntegrationTest {

    @Autowired
    private ApplicationContext context;

    @Autowired
    private MongoOperations mongoOperations;

    private WebTestClient webTestClient;

    @Before
    public void setUp() {
        var savedTask = TaskTestData.tasks().simple();
        var savedSearchCriteria = savedTask.getSearchCriteria().get(0);

        webTestClient = WebTestClient.bindToApplicationContext(context)
                .apply(springSecurity())
                .configureClient()
                .filter(basicAuthentication())
                .responseTimeout(Duration.ofDays(1))
                .build();

        mongoOperations.save(savedSearchCriteria);
        mongoOperations.save(savedTask);
    }

    @After
    public void tearDown() {
        mongoOperations.dropCollection(Task.class);
    }

    @Test
    public void shouldCreateFindAndRemoveTask() {
//        @formatter:off
        var actual = webTestClient
                .post()
                .uri("/v1/tasks")
                .header(HttpHeaders.AUTHORIZATION, PrincipalMock.getAuthorizationHeader(Tasks.SIMPLE_USER_ID))
                .body(Mono.just(CommonTestData.tasks().withNewCriteria()), TaskDto.class)
                .exchange()
                    .expectStatus()
                        .isCreated()
                    .expectBody(TaskDto.class)
                        .returnResult()
                        .getResponseBody();
//        @formatter:on

        assertNotNull(actual);

        var id = actual.getId();

//        @formatter:off
        var expected = webTestClient.get()
                .uri("/v1/tasks/{name}/{id}", Tasks.SIMPLE_USER_ID, id)
                .header(HttpHeaders.AUTHORIZATION, PrincipalMock.getAuthorizationHeader(Tasks.SIMPLE_USER_ID))
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
                .uri("/v1/tasks/{id}", id)
                .header(HttpHeaders.AUTHORIZATION, PrincipalMock.getAuthorizationHeader(Tasks.SIMPLE_USER_ID))
                .exchange()
                    .expectStatus()
                        .isNoContent();

        webTestClient.get()
                .uri("/v1/tasks/{name}/{id}", Tasks.SIMPLE_USER_ID, id)
                .header(HttpHeaders.AUTHORIZATION, PrincipalMock.getAuthorizationHeader(Tasks.SIMPLE_USER_ID))
                .exchange()
                    .expectStatus()
                        .isNotFound();
//        @formatter:on
    }

    @Test
    public void shouldFindAll() {
//        @formatter:off
        var actual = webTestClient.get()
                .uri("/v1/tasks")
                .header(HttpHeaders.AUTHORIZATION, PrincipalMock.getAuthorizationHeader(Tasks.SIMPLE_USER_ID))
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