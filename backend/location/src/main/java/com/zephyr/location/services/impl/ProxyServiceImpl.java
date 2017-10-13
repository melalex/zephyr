package com.zephyr.location.services.impl;

import com.zephyr.data.ProxyDto;
import com.zephyr.location.domain.Proxy;
import com.zephyr.location.repositories.ProxyRepository;
import com.zephyr.location.services.ProxyService;
import com.zephyr.location.services.converters.Converter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

@Service
public class ProxyServiceImpl implements ProxyService {

    @Setter(onMethod = @__(@Autowired))
    private ProxyRepository proxyRepository;

    @Setter(onMethod = @__(@Autowired))
    private Converter<Proxy, ProxyDto> proxyConverter;

    @Override
    public Flux<ProxyDto> findByCountryIsoCode(String iso) {
        return proxyRepository.findAllByCountryIso(iso)
                .map(proxyConverter::convert);
    }

    @Override
    public Flux<String> findProxiesLocations() {
        return proxyRepository.findProxiesLocations();
    }
}
