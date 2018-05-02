package com.zephyr.agent.controllers;

import com.zephyr.agent.services.UserAgentService;
import com.zephyr.data.protocol.dto.UserAgentDto;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@AllArgsConstructor
@RequestMapping("/v1/user-agents")
public class UserAgentController {

    private UserAgentService userAgentService;

    @GetMapping
    public Mono<UserAgentDto> findOne(String device, String osName, String browser) {
        return userAgentService.findOneByExample(device, osName, browser);
    }
}
