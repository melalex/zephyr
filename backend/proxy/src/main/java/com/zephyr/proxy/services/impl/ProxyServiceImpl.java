package com.zephyr.proxy.services.impl;

import com.zephyr.data.dto.ProxyDto;
import com.zephyr.data.enums.SearchEngine;
import com.zephyr.proxy.domain.Proxy;
import com.zephyr.proxy.repositories.ProxyRepository;
import com.zephyr.proxy.services.ProxyService;
import lombok.Setter;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;

@Service
public class ProxyServiceImpl implements ProxyService {

    @Setter(onMethod = @__(@Autowired))
    private ProxyRepository proxyRepository;

    @Setter(onMethod = @__(@Autowired))
    private ModelMapper mapper;

    @Override
    public Mono<ProxyDto> reserve(SearchEngine engine) {
        return proxyRepository.reserve(engine)
                .map(p -> toProxyDto(p, engine));
    }

    @Override
    public Mono<Void> report(String id, SearchEngine engine) {
        return proxyRepository.report(id, engine)
                .then();
    }

    @Override
    public Mono<Void> resetFailedProxies() {
        return proxyRepository.resetFailedProxies()
                .then();
    }

    private ProxyDto toProxyDto(Proxy proxy, SearchEngine engine) {
        ProxyDto dto = mapper.map(proxy, ProxyDto.class);
        dto.setSchedule(toLocalDateTime(proxy.getScheduledUsage().get(engine)));

        return dto;
    }

    private LocalDateTime toLocalDateTime(Long millis) {
        return LocalDateTime.ofInstant(Instant.ofEpochMilli(millis), ZoneId.systemDefault());
    }
}
