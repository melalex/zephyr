package com.zephyr.task.controllers;

import com.zephyr.data.protocol.dto.SearchCriteriaDto;
import com.zephyr.task.facades.SearchCriteriaFacade;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@AllArgsConstructor
@RequestMapping("/v1/search-criteria")
public class SearchCriteriaController {

    private SearchCriteriaFacade searchCriteriaFacade;

    @GetMapping
    public Flux<SearchCriteriaDto> findAll(Pageable pageable) {
        return searchCriteriaFacade.findAll(pageable);
    }

    @GetMapping("/similar")
    public Flux<SearchCriteriaDto> findByExample(Mono<SearchCriteriaDto> example, Sort sort) {
        return searchCriteriaFacade.findByExample(example, sort);
    }
}
