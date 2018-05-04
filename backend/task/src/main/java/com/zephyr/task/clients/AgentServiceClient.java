package com.zephyr.task.clients;

import com.zephyr.data.protocol.dto.UserAgentDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import reactor.core.publisher.Mono;

@FeignClient("agent-client")
public interface AgentServiceClient {

    @GetMapping
    UserAgentDto findOneByExample(String device, String osName, String browser);

    // TODO: Feign not supported Reactor yet
    default Mono<UserAgentDto> findOneByExampleAsync(String device, String osName, String browser) {
        return Mono.just(findOneByExample(device, osName, browser));
    }
}
