package com.zephyr.rating.cliensts;

import com.zephyr.data.dto.TaskDto;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import reactor.core.publisher.Mono;

@FeignClient("task-client")
public interface TaskServiceClient {

    @GetMapping("/{id}")
    Mono<TaskDto> findById(@PathVariable("id") String id);
}
