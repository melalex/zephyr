package com.zephyr.scraper.crawler;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.zephyr.scraper.ScraperTestConfiguration;
import com.zephyr.scraper.data.ScraperTestData;
import com.zephyr.scraper.exceptions.FraudException;
import com.zephyr.test.Results;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest
@RunWith(SpringRunner.class)
public class CrawlerIntegrationTest {

    @Autowired
    private Crawler testInstance;

    @Test
    public void shouldCrawlBing() {
        var response = ScraperTestData.responses().bing();

        var actual = testInstance.crawl(response);

        Assert.assertEquals(Results.BING_LINKS, actual);
    }

    @Test
    public void shouldCrawlGoogle() {
        var response = ScraperTestData.responses().google();

        var actual = testInstance.crawl(response);

        Assert.assertEquals(Results.GOOGLE_LINKS, actual);

    }

    @Test
    public void shouldCrawlYahoo() {
        var response = ScraperTestData.responses().yahoo();

        var actual = testInstance.crawl(response);

        Assert.assertEquals(Results.YAHOO_LINKS, actual);
    }

    @Test
    public void shouldTrowFraudExceptionForGoogle() {
        var response = ScraperTestData.responses().googleFraud();

        assertThatThrownBy(() -> testInstance.crawl(response))
                .isExactlyInstanceOf(FraudException.class);
    }

    @TestConfiguration
    @Import(ScraperTestConfiguration.class)
    public static class Configuration {

    }
}