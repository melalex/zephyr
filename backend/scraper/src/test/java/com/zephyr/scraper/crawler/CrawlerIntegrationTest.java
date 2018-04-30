package com.zephyr.scraper.crawler;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.zephyr.scraper.ScraperTestConfiguration;
import com.zephyr.scraper.data.ScraperTestData;
import com.zephyr.scraper.domain.EngineResponse;
import com.zephyr.scraper.exceptions.FraudException;
import com.zephyr.test.SearchResults;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@SpringBootTest
@RunWith(SpringRunner.class)
@Import(ScraperTestConfiguration.class)
public class CrawlerIntegrationTest {

    @Autowired
    private Crawler testInstance;

    @Test
    public void shouldCrawlBing() {
        EngineResponse response = ScraperTestData.responses().bing();

        List<String> actual = testInstance.crawl(response);

        Assert.assertEquals(SearchResults.BING_LINKS, actual);
    }

    @Test
    public void shouldCrawlGoogle() {
        EngineResponse response = ScraperTestData.responses().google();

        List<String> actual = testInstance.crawl(response);

        Assert.assertEquals(SearchResults.GOOGLE_LINKS, actual);

    }

    @Test
    public void shouldCrawlYahoo() {
        EngineResponse response = ScraperTestData.responses().yahoo();

        List<String> actual = testInstance.crawl(response);

        Assert.assertEquals(SearchResults.YAHOO_LINKS, actual);
    }

    @Test
    public void shouldTrowFraudExceptionForGoogle() {
        EngineResponse response = ScraperTestData.responses().googleFraud();

        assertThatThrownBy(() -> testInstance.crawl(response))
                .isExactlyInstanceOf(FraudException.class);
    }
}