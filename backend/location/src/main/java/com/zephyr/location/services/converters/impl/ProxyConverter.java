package com.zephyr.location.services.converters.impl;

import com.zephyr.data.ProxyDto;
import com.zephyr.location.domain.Proxy;
import com.zephyr.location.services.converters.Converter;
import org.springframework.stereotype.Component;

@Component
public class ProxyConverter implements Converter<Proxy, ProxyDto> {

    @Override
    public ProxyDto convert(Proxy proxy) {
        ProxyDto result = new ProxyDto();

        result.setId(proxy.getId());
        result.setCountryIso(proxy.getCountryIso());
        result.setIp(proxy.getIp());
        result.setPort(proxy.getPort());
        result.setProtocol(proxy.getProtocol());

        return result;
    }
}