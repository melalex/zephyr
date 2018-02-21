package com.zephyr.keyword.services.manager.impl;

import com.zephyr.keyword.properties.KeywordProperties;
import com.zephyr.keyword.properties.KeywordSource;
import com.zephyr.keyword.services.manager.KeywordManager;
import com.zephyr.keyword.services.provider.KeywordsProvider;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class KeywordManagerImpl implements KeywordManager {
    private static final String ERROR_MESSAGE = "Unknown keyword provider '%s'";

    @Setter(onMethod = @__(@Autowired))
    private KeywordProperties properties;

    @Setter(onMethod = @__(@Autowired))
    private KeywordsProvider adWordsKeywordsProvider;

    @Setter(onMethod = @__(@Autowired))
    private KeywordsProvider ratingServiceProvider;

    @Override
    public KeywordsProvider manage() {
        KeywordSource provider = properties.getProvider();
        switch (provider) {
            case AD_WORDS:
                return adWordsKeywordsProvider;
            case RATING_SERVICE:
                return ratingServiceProvider;
            default:
                throw new IllegalStateException(String.format(ERROR_MESSAGE, provider));
        }
    }
}
