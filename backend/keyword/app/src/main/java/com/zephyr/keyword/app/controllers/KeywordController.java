package com.zephyr.keyword.app.controllers;

import com.zephyr.data.protocol.criteria.KeywordCriteria;
import com.zephyr.data.protocol.vo.KeywordVo;
import com.zephyr.keyword.business.services.KeywordService;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

import javax.validation.Valid;

@RestController("/v1/keywords")
public class KeywordController {

    @Setter(onMethod = @__(@Autowired))
    private KeywordService keywordService;

    @GetMapping
    public Flux<KeywordVo> findKeywords(@Valid KeywordCriteria request) {
        return keywordService.findKeywords(request);
    }
}
