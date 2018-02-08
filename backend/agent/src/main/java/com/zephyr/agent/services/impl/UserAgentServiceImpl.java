package com.zephyr.agent.services.impl;

import com.zephyr.agent.domain.UserAgent;
import com.zephyr.agent.repositories.UserAgentRepository;
import com.zephyr.agent.services.UserAgentService;
import com.zephyr.data.dto.UserAgentDto;
import com.zephyr.commons.extensions.ExtendedMapper;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class UserAgentServiceImpl implements UserAgentService {

    @Setter(onMethod = @__(@Autowired))
    private UserAgentRepository userAgentRepository;

    @Setter(onMethod = @__(@Autowired))
    private ExtendedMapper mapper;

    @Override
    public Flux<UserAgentDto> findByExample(UserAgentDto userAgentDto) {
        return mapper.mapAsync(userAgentDto, UserAgent.class)
                .map(Example::of)
                .flatMapMany(userAgentRepository::findAll)
                .map(mapper.mapperFor(UserAgentDto.class));
    }

    @Override
    public Mono<UserAgentDto> findByOneExample(UserAgentDto userAgentDto) {
        return mapper.mapAsync(userAgentDto, UserAgent.class)
                .map(Example::of)
                .flatMap(userAgentRepository::findOne)
                .map(mapper.mapperFor(UserAgentDto.class));
    }

    @Override
    public Mono<UserAgentDto> random() {
        return userAgentRepository.random()
                .map(mapper.mapperFor(UserAgentDto.class));
    }
}
