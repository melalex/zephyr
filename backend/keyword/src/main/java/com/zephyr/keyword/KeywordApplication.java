package com.zephyr.keyword;

import com.zephyr.commons.interfaces.Manager;
import com.zephyr.commons.support.DefaultManager;
import com.zephyr.keyword.properties.KeywordSource;
import com.zephyr.keyword.services.provider.KeywordsProvider;
import com.zephyr.keyword.services.provider.impl.AdWordsKeywordsProvider;
import com.zephyr.keyword.services.provider.impl.RatingServiceProvider;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.context.annotation.Bean;

import java.util.Map;

import static java.util.Map.entry;

@EnableFeignClients
@SpringBootApplication
public class KeywordApplication {

    public static void main(String[] args) {
        SpringApplication.run(KeywordApplication.class, args);
    }

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
