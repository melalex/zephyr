package com.zephyr.agent.business;

import com.zephyr.data.protocol.dto.UserAgentDto;
import reactor.core.publisher.Mono;

public interface UserAgentService {

    Mono<UserAgentDto> findOneByExample(String device, String osName, String browser);
}
