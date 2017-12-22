package com.zephyr.task.services.clients;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;

import java.security.Principal;

@FeignClient(name = "account-service")
public interface AuthServiceClient {

    @GetMapping(value = "/current", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    Principal current();
}
