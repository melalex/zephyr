package com.zephyr.proxy.services.impl;

import com.zephyr.data.dto.ProxyDto;
import com.zephyr.data.enums.SearchEngine;
import com.zephyr.mapping.mappers.ExtendedMapper;
import com.zephyr.proxy.domain.Proxy;
import com.zephyr.proxy.repositories.ProxyRepository;
import com.zephyr.proxy.services.ProxyService;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import static java.util.Objects.nonNull;

@Service
public class ProxyServiceImpl implements ProxyService {

    @Setter(onMethod = @__(@Autowired))
    private ProxyRepository proxyRepository;

    @Setter(onMethod = @__(@Autowired))
    private ExtendedMapper mapper;

    @Override
    public Mono<ProxyDto> reserve(SearchEngine engine) {
        return proxyRepository.reserve(engine)
                .map(mapper.mapperFor(ProxyDto.class));
    }

    @Override
    public Mono<Void> report(String id, SearchEngine engine) {
        return proxyRepository.findById(id)
                .map(p -> incrementFailsCount(p, engine))
                .doOnNext(proxyRepository::save)
                .then();
    }

    private Proxy incrementFailsCount(Proxy proxy, SearchEngine engine) {
        proxy.getFailsCount()
                .compute(engine, (k, v) -> nonNull(v) ? v + 1 : 1);

        return proxy;
    }
}
