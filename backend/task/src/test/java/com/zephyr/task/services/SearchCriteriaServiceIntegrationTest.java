package com.zephyr.task.services;

import static com.zephyr.test.CommonTestData.queries;
import static com.zephyr.test.matchers.StreamMatcher.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.springframework.cloud.stream.test.matcher.MessageQueueMatcher.receivesPayloadThat;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.zephyr.data.internal.dto.QueryDto;
import com.zephyr.task.TaskTestConfiguration;
import com.zephyr.task.data.TaskTestData;
import com.zephyr.task.domain.SearchCriteria;
import com.zephyr.task.repositories.SearchCriteriaRepository;
import com.zephyr.test.CommonTestData;
import com.zephyr.test.Criteria;
import com.zephyr.test.matchers.StreamMatcher;
import com.zephyr.test.mocks.TimeMachine;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.cloud.stream.messaging.Source;
import org.springframework.cloud.stream.test.binder.MessageCollector;
import org.springframework.context.annotation.Import;
import org.springframework.messaging.Message;
import org.springframework.test.context.junit4.SpringRunner;
import reactor.test.StepVerifier;

import java.time.LocalDateTime;
import java.util.concurrent.BlockingQueue;

@SpringBootTest
@RunWith(SpringRunner.class)
public class SearchCriteriaServiceIntegrationTest {

    @Autowired
    private Source source;

    @Autowired
    private MessageCollector collector;

    @Autowired
    private SearchCriteriaRepository searchCriteriaRepository;

    @Autowired
    private SearchCriteriaService testInstance;

    @Autowired
    private ConfigurationService configurationService;

    @Autowired
    private TimeMachine timeMachine;

    @Autowired
    private ObjectMapper objectMapper;

    private SearchCriteria criteria1;
    private SearchCriteria criteria2;

    @Before
    public void setUp() {
        LocalDateTime unRelevantCriteria = timeMachine.now().minus(configurationService.getRelevancePeriod())
                .minusDays(1);

        criteria1 = searchCriteriaRepository.save(TaskTestData.criteria()
                .newCriteria(Criteria.QUERY1, Criteria.HIT_LOWER_PRIORITY, unRelevantCriteria))
                .block();

        criteria2 = searchCriteriaRepository.save(TaskTestData.criteria()
                .newCriteria(Criteria.QUERY2, Criteria.HIT_HIGHER_PRIORITY, unRelevantCriteria))
                .block();

        searchCriteriaRepository.save(TaskTestData.criteria().simple()).subscribe();
    }

    @After
    public void tearDown() {
        searchCriteriaRepository.deleteAll().subscribe();
    }

    @Test
    public void shouldCreate() {
        SearchCriteria simple = TaskTestData.criteria().simple();

        StepVerifier.create(testInstance.updateSearchCriteria(simple))
                .consumeNextWith(c -> searchCriteriaRepository.findById(c.getId())
                        .doOnNext(s -> Assert.assertEquals(s.getHitsCount(), Criteria.SIMPLE_HITS_COUNT + 1))
                        .doOnNext(s -> Assert.assertEquals(s.getLastHit(), timeMachine.now()))
                        .subscribe()
                )
                .verifyComplete();
    }

    @Test
    public void shouldCreateWithNewCriteria() {
        SearchCriteria newCriteria = TaskTestData.criteria().newCriteria();
        newCriteria.setUserAgent(null);

        StepVerifier.create(testInstance.updateSearchCriteria(newCriteria))
                .consumeNextWith(c -> searchCriteriaRepository.findById(c.getId())
                        .doOnNext(s -> Assert.assertEquals(s.getHitsCount(), 1))
                        .doOnNext(s -> Assert.assertEquals(s.getLastHit(), timeMachine.now()))
                        .doOnNext(s -> Assert.assertEquals(s.getLastUpdate(), timeMachine.now()))
                        .subscribe()
                )
                .verifyComplete();

        BlockingQueue<Message<?>> messages = collector.forChannel(source.output());

        assertThat(messages, payload(objectMapper, QueryDto.class).matches(is(queries().withoutAgent())));
    }

    @Test
    public void shouldFindAllForUpdate() {
        StepVerifier.create(testInstance.findAllForUpdate())
                .consumeNextWith(c -> Assert.assertEquals(criteria2.getId(), c.getId()))
                .consumeNextWith(c -> Assert.assertEquals(criteria1.getId(), c.getId()))
                .verifyComplete();
    }


    @TestConfiguration
    @Import(TaskTestConfiguration.class)
    public static class Configuration {

    }
}