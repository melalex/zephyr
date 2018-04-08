package com.zephyr.agent.app.controllers;

import com.zephyr.agent.business.UserAgentService;
import com.zephyr.data.protocol.dto.UserAgentDto;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/v1/user-agents")
public class UserAgentController {

    @Setter(onMethod = @__(@Autowired))
    private UserAgentService userAgentService;

    @GetMapping
    public Mono<UserAgentDto> findOne(String device, String osName, String browser) {
        return userAgentService.findOneByExample(device, osName, browser);
    }
}
