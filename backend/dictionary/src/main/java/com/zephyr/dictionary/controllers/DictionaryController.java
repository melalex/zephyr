package com.zephyr.dictionary.controllers;

import com.zephyr.commons.data.Keyword;
import com.zephyr.dictionary.services.DictionaryService;
import com.zephyr.dictionary.services.dto.DictionaryDto;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

@RestController
@RequestMapping("/v1/dictionaries")
public class DictionaryController {

    @Setter(onMethod = @__(@Autowired))
    private DictionaryService dictionaryService;

    @PostMapping("/update")
    public Flux<DictionaryDto> update(Flux<Keyword> keywords) {
        return dictionaryService.update(keywords);
    }
}
