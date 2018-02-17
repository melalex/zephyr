package com.zephyr.keyword.services.impl;

import com.zephyr.data.protocol.criteria.KeywordCriteria;
import com.zephyr.data.protocol.vo.KeywordVo;
import com.zephyr.keyword.services.KeywordService;
import com.zephyr.keyword.services.manager.KeywordManager;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

import javax.validation.Valid;

@Service
public class KeywordServiceImpl implements KeywordService {

    @Setter(onMethod = @__(@Autowired))
    private KeywordManager keywordManager;

    @Override
    public Flux<KeywordVo> findKeywords(@Valid KeywordCriteria request) {
        return keywordManager.manage().provide(request);
    }
}
