package com.zephyr.scraper;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.springframework.cloud.stream.test.matcher.MessageQueueMatcher.receivesPayloadThat;

import com.zephyr.data.internal.dto.QueryDto;
import com.zephyr.test.CommonTestData;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.cloud.stream.messaging.Processor;
import org.springframework.cloud.stream.test.binder.MessageCollector;
import org.springframework.context.annotation.Import;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.GenericMessage;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.concurrent.BlockingQueue;

@SpringBootTest
@ActiveProfiles("streamTest")
@RunWith(SpringRunner.class)
public class ScraperApplicationTests {

    @Autowired
    private Processor processor;

    @Autowired
    private MessageCollector collector;

    @Test
    public void shouldHandleQuery() {
        Message<QueryDto> testMessage = new GenericMessage<>(CommonTestData.queries().simple());

        processor.input().send(testMessage);

        BlockingQueue<Message<?>> actual = collector.forChannel(processor.output());

        assertThat(actual, receivesPayloadThat(is(CommonTestData.searchResults().bing())));
        assertThat(actual, receivesPayloadThat(is(CommonTestData.searchResults().google())));
        assertThat(actual, receivesPayloadThat(is(CommonTestData.searchResults().yahoo())));
    }

    @TestConfiguration
    @Import(ScraperTestConfiguration.class)
    public static class Configuration {

    }
}