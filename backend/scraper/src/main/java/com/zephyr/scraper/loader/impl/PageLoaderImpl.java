package com.zephyr.scraper.loader.impl;

import com.zephyr.data.Keyword;
import com.zephyr.data.criteria.ProxyCriteria;
import com.zephyr.data.enums.Protocol;
import com.zephyr.scraper.clients.LocationServiceClient;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.util.Set;

@Component
@RefreshScope
public class PageLoaderImpl extends AbstractPageLoader {
    private static final int FIRST_PAGE = 0;

    @Setter(onMethod = @__(@Autowired))
    private LocationServiceClient locationServiceClient;

    @Value("${scraper.proxy.enabled}")
    private boolean isUseProxy;

    @Value("${scraper.proxy.requestSize}")
    private int requestSize;

    @Override
    protected Mono<String> getHtml(Keyword keyword) {
        if (isUseProxy) {
            return locationServiceClient
                    .findProxyByCriteria(newCriteria(keyword.getCountryIso()), newPageRequest())
                    .map(p -> "");
        }

        return Mono.empty();
    }

    private Pageable newPageRequest() {
        return new PageRequest(FIRST_PAGE, requestSize);
    }

    private ProxyCriteria newCriteria(String countryIso) {
        ProxyCriteria criteria = new ProxyCriteria();

        criteria.setCountryIso(countryIso);
        criteria.setProtocols(Set.of(Protocol.HTTPS));

        return criteria;
    }
}