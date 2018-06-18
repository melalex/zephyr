package com.zephyr.scraper.mocks;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import com.zephyr.scraper.configuration.ScraperConfigurationService;
import com.zephyr.scraper.data.ScraperTestProperties;
import lombok.experimental.UtilityClass;

@UtilityClass
public class ConfigurationMock {

    public ScraperConfigurationService of() {
        var mock = mock(ScraperConfigurationService.class);
        when(mock.getBackOff()).thenReturn(ScraperTestProperties.BACKOFF);
        when(mock.getDelay(any())).thenReturn(ScraperTestProperties.DELAY);
        when(mock.getErrorDelay(any())).thenReturn(ScraperTestProperties.ERROR_DELAY);
        when(mock.getRetryCount()).thenReturn(ScraperTestProperties.RETRY_COUNT);

        return mock;
    }
}
