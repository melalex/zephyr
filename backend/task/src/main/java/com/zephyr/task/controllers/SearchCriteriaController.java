package com.zephyr.task.controllers;

import com.zephyr.task.facades.MeteredSearchCriteriaFacade;
import com.zephyr.task.facades.dto.MeteredSearchCriteriaDto;
import com.zephyr.task.facades.dto.SearchCriteriaDto;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/v1/search-criteria")
public class SearchCriteriaController {

    @Setter(onMethod = @__(@Autowired))
    private MeteredSearchCriteriaFacade meteredSearchCriteriaFacade;

    @GetMapping
    public Flux<MeteredSearchCriteriaDto> findAll(Pageable pageable) {
        return meteredSearchCriteriaFacade.findAll(pageable);
    }

    @GetMapping("/similar")
    public Flux<MeteredSearchCriteriaDto> findByExample(Mono<SearchCriteriaDto> example, Sort sort) {
        return meteredSearchCriteriaFacade.findByExample(example, sort);
    }
}
