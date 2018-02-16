package com.zephyr.task.integration.clients;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import reactor.core.publisher.Mono;

import java.security.Principal;

@FeignClient(name = "account-service")
public interface AuthServiceClient {

    @GetMapping(value = "/current")
    Mono<Principal> current();
}
