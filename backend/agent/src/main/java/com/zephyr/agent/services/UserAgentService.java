package com.zephyr.agent.services;

import com.zephyr.data.protocol.dto.UserAgentDto;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface UserAgentService {

    Flux<UserAgentDto> findAllByExample(UserAgentDto userAgentDto);

    Mono<UserAgentDto> findOneByExample(UserAgentDto userAgentDto);

    Mono<UserAgentDto> random();
}
