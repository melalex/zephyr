package com.zephyr.keyword.clients;

import com.zephyr.data.protocol.dto.KeywordDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import reactor.core.publisher.Flux;

import java.util.List;

@FeignClient("rating")
public interface RatingServiceClient {

    @GetMapping
    List<KeywordDto> findRatingForUrl(@RequestParam("url") String url, @RequestParam("page") int page,
                                      @RequestParam("size") int size);


    default Flux<KeywordDto> findRatingForUrlAsync(String url, int page, int size) {
        return Flux.fromIterable(findRatingForUrl(url, page, size));
    }
}
