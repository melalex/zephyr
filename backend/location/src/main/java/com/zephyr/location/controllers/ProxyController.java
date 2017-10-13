package com.zephyr.location.controllers;

import com.zephyr.data.ProxyDto;
import com.zephyr.location.services.ProxyService;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

@RestController
@RequestMapping("/v1/proxy")
public class ProxyController {

    @Setter(onMethod = @__(@Autowired))
    private ProxyService proxyService;

    @RequestMapping("/{iso}")
    public Flux<ProxyDto> findByCountryIsoCode(@PathVariable("iso") String iso) {
        return proxyService.findByCountryIsoCode(iso);
    }
}