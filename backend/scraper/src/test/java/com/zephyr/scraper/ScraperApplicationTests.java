package com.zephyr.scraper;

import static com.zephyr.test.matchers.StreamMatcher.payload;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.zephyr.data.internal.dto.QueryDto;
import com.zephyr.data.internal.dto.SearchResultDto;
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

        var actual = collector.forChannel(processor.output());

        var expectedBing = CommonTestData.searchResults().bing();
        var expectedGoogle = CommonTestData.searchResults().google();
        var expectedYahoo = CommonTestData.searchResults().yahoo();

        assertThat(actual, payload(objectMapper, SearchResultDto.class).matches(is(expectedBing)));
        assertThat(actual, payload(objectMapper, SearchResultDto.class).matches(is(expectedGoogle)));
        assertThat(actual, payload(objectMapper, SearchResultDto.class).matches(is(expectedYahoo)));
    }

    @TestConfiguration
    @Import(ScraperTestConfiguration.class)
    public static class Configuration {

    }
}