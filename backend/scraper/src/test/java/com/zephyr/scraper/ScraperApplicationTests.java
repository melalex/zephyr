package com.zephyr.scraper;

import static org.junit.Assert.assertEquals;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.zephyr.data.internal.dto.QueryDto;
import com.zephyr.data.internal.dto.SearchResultDto;
import com.zephyr.test.CommonTestData;
import lombok.SneakyThrows;
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

import java.util.Set;
import java.util.concurrent.BlockingQueue;

@SpringBootTest
@ActiveProfiles("streamTest")
@RunWith(SpringRunner.class)
public class ScraperApplicationTests {

    @Autowired
    private Processor processor;

    @Autowired
    private MessageCollector collector;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void shouldHandleQuery() {
        Message<QueryDto> testMessage = new GenericMessage<>(CommonTestData.queries().simple());

        processor.input().send(testMessage);

        var actualQueue = collector.forChannel(processor.output());

        var actual = Set.of(fetch(actualQueue), fetch(actualQueue), fetch(actualQueue));

        var expected = Set.of(
                CommonTestData.searchResults().bing(),
                CommonTestData.searchResults().google(),
                CommonTestData.searchResults().yahoo()
        );

        assertEquals(expected, actual);
    }

    @SneakyThrows
    private SearchResultDto fetch(BlockingQueue<Message<?>> queue) {
        var content = queue.take().getPayload().toString();
        return objectMapper.readValue(content, SearchResultDto.class);
    }

    @TestConfiguration
    @Import(ScraperTestConfiguration.class)
    public static class Configuration {

    }
}