package com.zephyr.task.services;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.springframework.cloud.stream.test.matcher.MessageQueueMatcher.receivesPayloadThat;

import com.zephyr.task.TaskTestConfiguration;
import com.zephyr.task.data.TaskTestData;
import com.zephyr.task.domain.SearchCriteria;
import com.zephyr.task.repositories.SearchCriteriaRepository;
import com.zephyr.test.CommonTestData;
import com.zephyr.test.Criteria;
import com.zephyr.test.mocks.ClockMock;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
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
@Import(TaskTestConfiguration.class)
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

    private SearchCriteria criteria1;
    private SearchCriteria criteria2;

    @Before
    public void setUp() {
        LocalDateTime unRelevantCriteria = ClockMock.now().minus(configurationService.getRelevancePeriod())
                .minusDays(1);

        searchCriteriaRepository.save(TaskTestData.criteria()
                .newCriteria(Criteria.QUERY1, Criteria.HIT_LOWER_PRIORITY, unRelevantCriteria))
                .subscribe(c -> criteria1 = c);

        searchCriteriaRepository.save(TaskTestData.criteria()
                .newCriteria(Criteria.QUERY2, Criteria.HIT_HIGHER_PRIORITY, unRelevantCriteria))
                .subscribe(c -> criteria2 = c);

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
                        .doOnNext(s -> Assert.assertEquals(s.getLastHit(), ClockMock.now()))
                        .subscribe()
                )
                .verifyComplete();
    }

    @Test
    public void shouldCreateWithNewCriteria() {
        SearchCriteria newCriteria = TaskTestData.criteria().newCriteria();

        StepVerifier.create(testInstance.updateSearchCriteria(newCriteria))
                .consumeNextWith(c -> searchCriteriaRepository.findById(c.getId())
                        .doOnNext(s -> Assert.assertEquals(s.getHitsCount(), 1))
                        .doOnNext(s -> Assert.assertEquals(s.getLastHit(), ClockMock.now()))
                        .doOnNext(s -> Assert.assertEquals(s.getLastUpdate(), ClockMock.now()))
                        .subscribe()
                )
                .verifyComplete();

        BlockingQueue<Message<?>> messages = collector.forChannel(source.output());

        assertThat(messages, receivesPayloadThat(is(CommonTestData.queries().withoutAgent())));
    }

    @Test
    public void shouldFindAllForUpdate() {
        StepVerifier.create(testInstance.findAllForUpdate())
                .expectNext(criteria2)
                .expectNext(criteria1)
                .verifyComplete();
    }
}