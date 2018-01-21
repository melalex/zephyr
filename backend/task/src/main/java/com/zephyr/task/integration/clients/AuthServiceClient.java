package com.zephyr.task.integration.clients;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import reactor.core.publisher.Mono;

import java.security.Principal;

@FeignClient(name = "account-service")
public interface AuthServiceClient {

    @GetMapping(value = "/current", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    Mono<Principal> current();
}
