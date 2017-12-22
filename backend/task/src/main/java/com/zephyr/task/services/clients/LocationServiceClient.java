package com.zephyr.task.services.clients;

import com.zephyr.data.dto.PlaceDto;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient("location-service")
public interface LocationServiceClient {

    @GetMapping("/{id}")
    PlaceDto findById(@PathVariable("id") long id);
}
