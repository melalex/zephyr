package com.zephyr.agent.services;

import com.zephyr.agent.services.dto.UserAgentDto;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Map;

public interface UserAgentService {

    Flux<Map<String, Object>> findByExample(UserAgentDto userAgentDto, List<String> fields);

    Mono<Map<String, Object>> random(List<String> fields);
}
