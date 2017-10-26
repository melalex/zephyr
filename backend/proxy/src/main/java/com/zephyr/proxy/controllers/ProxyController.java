package com.zephyr.proxy.controllers;

import com.zephyr.data.dto.ProxyDto;
import com.zephyr.data.enums.SearchEngine;
import com.zephyr.proxy.services.ProxyService;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/v1/proxy")
public class ProxyController {

    @Setter(onMethod = @__(@Autowired))
    private ProxyService proxyService;

    @PutMapping("/reserve")
    public Mono<ProxyDto> reserve(SearchEngine engine) {
        return proxyService.reserve(engine);
    }

    @PutMapping("/report")
    public Mono<Void> report(String id, SearchEngine engine) {
        return proxyService.report(id, engine);
    }
}