package com.zephyr.location.services.impl;

import com.zephyr.commons.DataAccessUtils;
import com.zephyr.data.criteria.ProxyCriteria;
import com.zephyr.location.repositories.ProxyRepository;
import com.zephyr.location.services.ProxyService;
import com.zephyr.location.services.dto.ProxyDto;
import com.zephyr.mapping.mappers.ExtendedMapper;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class ProxyServiceImpl implements ProxyService {

    @Setter(onMethod = @__(@Autowired))
    private ProxyRepository proxyRepository;

    @Setter(onMethod = @__(@Autowired))
    private ExtendedMapper mapper;

    @Override
    public Mono<Page<ProxyDto>> findByCountryIsoCode(ProxyCriteria criteria, Pageable pageable) {
        Flux<ProxyDto> content = proxyRepository
                .findByCriteria(criteria, pageable)
                .map(mapper.mapperFor(ProxyDto.class));

        Mono<Long> count = proxyRepository.countByCriteria(criteria);

        return DataAccessUtils.toReactivePage(content, count, pageable);
    }

    @Override
    public Flux<String> findProxiesLocations() {
        return proxyRepository.findProxiesLocations();
    }
}
