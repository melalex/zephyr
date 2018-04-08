package com.zephyr.keyword.business.clients;

import com.zephyr.data.protocol.dto.RatingDto;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import reactor.core.publisher.Flux;

@FeignClient("task-client")
public interface RatingServiceClient {

    @GetMapping
    Flux<RatingDto> findRatingForUrl(String url, int page, @RequestParam("page.size") int pageSize);
}
