package com.zephyr.task.integration.clients;

import com.zephyr.data.dto.UserAgentDto;
import com.zephyr.task.domain.criteria.UserAgentCriteria;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import reactor.core.publisher.Flux;

@FeignClient("agent-client")
public interface AgentServiceClient {

    @GetMapping
    Flux<UserAgentDto> findByExample(UserAgentCriteria userAgentDto);
}
