package com.zephyr.location.controllers;

import com.zephyr.data.criteria.ProxyCriteria;
import com.zephyr.data.resources.ProxyResource;
import com.zephyr.location.services.ProxyService;
import com.zephyr.location.services.dto.ProxyDto;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.ExposesResourceFor;
import org.springframework.hateoas.PagedResources;
import org.springframework.hateoas.ResourceAssembler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/v1/proxy")
@ExposesResourceFor(ProxyResource.class)
public class ProxyController {

    @Setter(onMethod = @__(@Autowired))
    private ProxyService proxyService;

    @Setter(onMethod = @__(@Autowired))
    private ResourceAssembler<ProxyDto, ProxyResource> proxyAssembler;

    @RequestMapping("/search")
    public Mono<PagedResources<ProxyResource>> findByCountryIsoCode(
            ProxyCriteria criteria,
            PagedResourcesAssembler<ProxyDto> pageAssembler,
            @PageableDefault(size = 20) Pageable pageable
    ) {
        return proxyService.findByCountryIsoCode(criteria, pageable)
                .map(d -> pageAssembler.toResource(d, proxyAssembler));
    }
}