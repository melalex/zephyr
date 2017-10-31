package com.zephyr.dictionary.controllers;

import com.zephyr.data.commons.Keyword;
import com.zephyr.dictionary.controllers.resources.DictionaryResource;
import com.zephyr.dictionary.services.DictionaryService;
import com.zephyr.dictionary.services.dto.DictionaryDto;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.ExposesResourceFor;
import org.springframework.hateoas.PagedResources;
import org.springframework.hateoas.ResourceAssembler;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@ExposesResourceFor(DictionaryResource.class)
@RequestMapping("/v1/dictionaries")
public class DictionaryController {

    @Setter(onMethod = @__(@Autowired))
    private DictionaryService dictionaryService;

    @Setter(onMethod = @__(@Autowired))
    private ResourceAssembler<DictionaryDto, DictionaryResource> resourceAssembler;

    @GetMapping
    public Mono<PagedResources<DictionaryResource>> findAll(Pageable pageable, PagedResourcesAssembler<DictionaryDto> pagedResourcesAssembler) {
        return dictionaryService
                .findAll(pageable)
                .map(p -> pagedResourcesAssembler.toResource(p, resourceAssembler));
    }

    @GetMapping("/{id}")
    public Mono<DictionaryResource> findById(@PathVariable("id") String id) {
        return dictionaryService
                .findById(id)
                .map(d -> resourceAssembler.toResource(d));
    }

    @GetMapping("/keyword")
    public Mono<PagedResources<DictionaryResource>> findByKeyword(Keyword keyword, Pageable pageable, PagedResourcesAssembler<DictionaryDto> pagedResourcesAssembler) {
        return dictionaryService
                .findByKeyword(keyword, pageable)
                .map(p -> pagedResourcesAssembler.toResource(p, resourceAssembler));
    }

    @PostMapping("/update")
    public Flux<DictionaryDto> update(Flux<Keyword> keywords) {
        return dictionaryService.update(keywords);
    }
}
