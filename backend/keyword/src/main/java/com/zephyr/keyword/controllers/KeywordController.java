package com.zephyr.keyword.controllers;

import com.zephyr.data.protocol.dto.KeywordDto;
import com.zephyr.data.protocol.request.KeywordRequest;
import com.zephyr.keyword.services.KeywordService;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

import javax.validation.Valid;

@RestController
@RequestMapping("/v1/keywords")
public class KeywordController {

    @Setter(onMethod = @__(@Autowired))
    private KeywordService keywordService;

    @GetMapping
    public Flux<KeywordDto> findKeywords(@Valid KeywordRequest request) {
        return keywordService.findKeywords(request);
    }
}
