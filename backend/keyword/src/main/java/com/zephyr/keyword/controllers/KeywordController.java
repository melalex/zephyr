package com.zephyr.keyword.controllers;

import com.zephyr.keyword.protocol.KeywordResponse;
import com.zephyr.keyword.protocol.KeywordRequest;
import com.zephyr.keyword.services.KeywordService;
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
    public Flux<KeywordResponse> findKeywords(@Valid KeywordRequest request) {
        return keywordService.findKeywords(request);
    }
}
