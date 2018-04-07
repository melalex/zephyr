package com.zephyr.keyword;

import static java.util.Map.entry;

import com.zephyr.commons.interfaces.Manager;
import com.zephyr.commons.support.DefaultManager;
import com.zephyr.keyword.properties.KeywordSource;
import com.zephyr.keyword.services.provider.KeywordsProvider;
import com.zephyr.keyword.services.provider.impl.AdWordsKeywordsProvider;
import com.zephyr.keyword.services.provider.impl.RatingServiceProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Map;

@Configuration
public class KeywordConfiguration {

    @Bean
    public Manager<KeywordSource, KeywordsProvider> keywordManager() {
        return DefaultManager.of(keywordMapping());
    }

    @Bean
    public Map<KeywordSource, KeywordsProvider> keywordMapping() {
        return Map.ofEntries(
                entry(KeywordSource.AD_WORDS, adWordsKeywordsProvider()),
                entry(KeywordSource.RATING_SERVICE, ratingServiceProvider())
        );
    }

    @Bean
    public KeywordsProvider adWordsKeywordsProvider() {
        return new AdWordsKeywordsProvider();
    }

    @Bean
    public KeywordsProvider ratingServiceProvider() {
        return new RatingServiceProvider();
    }
}
