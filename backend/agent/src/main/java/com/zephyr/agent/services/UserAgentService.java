package com.zephyr.agent.services;

import com.zephyr.data.dto.UserAgentDto;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface UserAgentService {

    Flux<UserAgentDto> findByExample(UserAgentDto userAgentDto);

    Mono<UserAgentDto> findByOneExample(UserAgentDto userAgentDto);

    Mono<UserAgentDto> random();
}
