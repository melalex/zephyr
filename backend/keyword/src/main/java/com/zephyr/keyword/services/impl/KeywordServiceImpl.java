package com.zephyr.keyword.services.impl;

import com.zephyr.commons.interfaces.Manager;
import com.zephyr.data.protocol.request.KeywordRequest;
import com.zephyr.data.protocol.dto.KeywordDto;
import com.zephyr.keyword.properties.KeywordProperties;
import com.zephyr.keyword.properties.KeywordSource;
import com.zephyr.keyword.services.KeywordService;
import com.zephyr.keyword.services.provider.KeywordsProvider;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

import javax.validation.Valid;

@Service
public class KeywordServiceImpl implements KeywordService {

    @Setter(onMethod = @__(@Autowired))
    private Manager<KeywordSource, KeywordsProvider> keywordManager;

    @Setter(onMethod = @__(@Autowired))
    private KeywordProperties properties;

    @Override
    public Flux<KeywordDto> findKeywords(@Valid KeywordRequest request) {
        return keywordManager.manage(properties.getProvider()).provide(request);
    }
}
