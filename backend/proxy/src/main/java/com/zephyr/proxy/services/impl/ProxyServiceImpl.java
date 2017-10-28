package com.zephyr.proxy.services.impl;

import com.zephyr.data.dto.ProxyDto;
import com.zephyr.data.enums.SearchEngine;
import com.zephyr.proxy.domain.Proxy;
import com.zephyr.proxy.properties.ProxyProperties;
import com.zephyr.proxy.repositories.ProxyRepository;
import com.zephyr.proxy.services.ProxyService;
import lombok.Setter;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.OptimisticLockingFailureException;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import reactor.retry.Retry;

import java.time.Duration;
import java.time.LocalDateTime;

@Service
public class ProxyServiceImpl implements ProxyService {

    @Setter(onMethod = @__(@Autowired))
    private ProxyRepository proxyRepository;

    @Setter(onMethod = @__(@Autowired))
    private ModelMapper mapper;

    @Setter(onMethod = @__(@Autowired))
    private ProxyProperties proxyProperties;

    @Override
    public Mono<ProxyDto> reserve(SearchEngine engine) {
        return proxyRepository.findForReservation(engine)
                .doOnNext(p -> p.getScheduledUsage().compute(engine, this::schedule))
                .flatMap(proxyRepository::save)
                .retryWhen(Retry.anyOf(OptimisticLockingFailureException.class))
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

    private LocalDateTime schedule(SearchEngine engine, LocalDateTime previous) {
        LocalDateTime now = LocalDateTime.now();
        Duration duration = Duration.ofMillis(proxyProperties.getEngineProperties(engine).getDelay());

        if (previous == null || previous.isBefore(now)) {
            return now;
        } else {
            return previous.plus(duration);
        }
    }

    private ProxyDto toProxyDto(Proxy proxy, SearchEngine engine) {
        ProxyDto dto = mapper.map(proxy, ProxyDto.class);
        dto.setSchedule(proxy.getScheduledUsage().get(engine));

        return dto;
    }
}
