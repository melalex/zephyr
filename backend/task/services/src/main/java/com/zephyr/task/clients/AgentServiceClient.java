package com.zephyr.task.clients;

import com.zephyr.data.protocol.dto.UserAgentDto;
import com.zephyr.task.domain.UserAgentCriteria;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import reactor.core.publisher.Mono;

@FeignClient("agent-client")
public interface AgentServiceClient {

    @GetMapping("/first")
    Mono<UserAgentDto> findByOneExample(UserAgentCriteria userAgent);
}
