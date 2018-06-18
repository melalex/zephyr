package com.zephyr.task.clients;

import com.zephyr.commons.support.Profiles;
import com.zephyr.data.protocol.dto.UserAgentDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.context.annotation.Profile;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import reactor.core.publisher.Mono;

@Profile(Profiles.NOT_TEST)
@FeignClient("agent-client")
public interface AgentServiceClient {

    @GetMapping
    UserAgentDto findOneByExample(@RequestParam("device") String device, @RequestParam("osName") String osName,
                                  @RequestParam("browser") String browser);

    // TODO: Feign doesn't support Reactor
    default Mono<UserAgentDto> findOneByExampleAsync(String device, String osName, String browser) {
        return Mono.justOrEmpty(findOneByExample(device, osName, browser));
    }
}
