package com.zephyr.task.controllers;

import com.zephyr.task.services.MeteredSearchCriteriaService;
import com.zephyr.task.services.dto.MeteredSearchCriteriaDto;
import com.zephyr.task.services.dto.SearchCriteriaDto;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

@RestController
@RequestMapping("/v1/search-criteria")
public class SearchCriteriaController {

    @Setter(onMethod = @__(@Autowired))
    private MeteredSearchCriteriaService meteredSearchCriteriaService;

    @GetMapping
    public Flux<MeteredSearchCriteriaDto> findAll(Pageable pageable) {
        return meteredSearchCriteriaService.findAll(pageable);
    }

    @GetMapping("/similar")
    public Flux<MeteredSearchCriteriaDto> findByExample(SearchCriteriaDto example, Sort sort) {
        return meteredSearchCriteriaService.findByExample(example, sort);
    }
}
