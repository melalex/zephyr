package com.zephyr.agent.controllers;

import com.zephyr.agent.services.UserAgentService;
import com.zephyr.data.protocol.dto.UserAgentDto;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/v1/user-agents")
public class UserAgentController {

    @Setter(onMethod = @__(@Autowired))
    private UserAgentService userAgentService;

    @GetMapping
    public Flux<UserAgentDto> findAllByExample(UserAgentDto userAgentDto) {
        return userAgentService.findAllByExample(userAgentDto);
    }

    @GetMapping("/first")
    public Mono<UserAgentDto> findOneByExample(UserAgentDto userAgentDto) {
        return userAgentService.findOneByExample(userAgentDto);
    }

    @GetMapping("/random")
    public Mono<UserAgentDto> random() {
        return userAgentService.random();
    }
}
