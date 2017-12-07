package com.zephyr.agent.controllers;

import com.zephyr.agent.services.UserAgentService;
import com.zephyr.agent.services.dto.UserAgentDto;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/v1/user-agents")
public class UserAgentController {

    @Setter(onMethod = @__(@Autowired))
    private UserAgentService userAgentService;

    @GetMapping
    public Flux<Map<String, Object>> findByExample(UserAgentDto userAgentDto, List<String> fields) {
        return userAgentService.findByExample(userAgentDto, fields);
    }

    @GetMapping("/random")
    public Mono<Map<String, Object>> random(List<String> fields) {
        return userAgentService.random(fields);
    }
}
