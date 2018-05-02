package com.zephyr.task.clients;

import com.zephyr.data.protocol.dto.UserAgentDto;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import reactor.core.publisher.Mono;

@FeignClient("agent-client")
public interface AgentServiceClient {

    @GetMapping
    Mono<UserAgentDto> findByOneExample(String device, String osName, String browser);
}
