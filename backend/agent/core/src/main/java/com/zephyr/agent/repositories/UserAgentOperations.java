package com.zephyr.agent.repositories;

import com.zephyr.agent.domain.UserAgent;
import reactor.core.publisher.Mono;

public interface UserAgentOperations {

    Mono<UserAgent> random();
}
