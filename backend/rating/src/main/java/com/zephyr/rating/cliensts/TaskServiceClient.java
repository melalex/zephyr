package com.zephyr.rating.cliensts;

import com.zephyr.data.protocol.dto.TaskDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import reactor.core.publisher.Mono;

@FeignClient("task-client")
public interface TaskServiceClient {

    @GetMapping("/{name}/{id}")
    TaskDto findByUserAndId(@PathVariable String name, @PathVariable String id);

    default Mono<TaskDto> findByUserAndIdAsync(String name, String id) {
        return Mono.just(findByUserAndId(name, id));
    }
}