package com.zephyr.task.controllers;

import com.zephyr.task.services.SearchCriteriaService;
import com.zephyr.task.services.dto.SearchCriteriaDto;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

@RestController
@RequestMapping("/v1/search-criteria")
public class SearchCriteriaController {

    @Setter(onMethod = @__(@Autowired))
    private SearchCriteriaService searchCriteriaService;

    @GetMapping
    public Flux<SearchCriteriaDto> findAll(Pageable pageable) {
        return searchCriteriaService.findAll(pageable);
    }
}
