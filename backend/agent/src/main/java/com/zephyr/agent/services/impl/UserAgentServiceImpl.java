package com.zephyr.agent.services.impl;

import com.zephyr.agent.domain.UserAgent;
import com.zephyr.agent.repositories.UserAgentRepository;
import com.zephyr.agent.services.UserAgentService;
import com.zephyr.agent.services.dto.UserAgentDto;
import com.zephyr.agent.services.filters.FieldFilter;
import com.zephyr.commons.extensions.ExtendedMapper;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Map;

@Service
public class UserAgentServiceImpl implements UserAgentService {

    @Setter(onMethod = @__(@Autowired))
    private UserAgentRepository userAgentRepository;

    @Setter(onMethod = @__(@Autowired))
    private ExtendedMapper mapper;

    @Setter
    private FieldFilter<UserAgent> userAgentFieldFilter;

    @Override
    public Flux<Map<String, Object>> findByExample(UserAgentDto userAgentDto, List<String> fields) {
        return userAgentRepository.findAll(Example.of(mapper.map(userAgentDto, UserAgent.class)))
                .map(u -> userAgentFieldFilter.filter(u, fields));
    }

    @Override
    public Mono<Map<String, Object>> random(List<String> fields) {
        return userAgentRepository.random()
                .map(u -> userAgentFieldFilter.filter(u, fields));
    }
}
