package com.zephyr.rating.cliensts;

import com.zephyr.commons.support.Profiles;
import com.zephyr.data.protocol.dto.TaskDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.context.annotation.Profile;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import reactor.core.publisher.Mono;

@Profile(Profiles.NOT_TEST)
@FeignClient("task")
public interface TaskServiceClient {

    @GetMapping("/{name}/{id}")
    TaskDto findByUserAndId(@PathVariable("name") String name, @PathVariable("id") String id);

    default Mono<TaskDto> findByUserAndIdAsync(String name, String id) {
        return Mono.just(findByUserAndId(name, id));
    }
}