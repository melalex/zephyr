package com.zephyr.rating.controllers;

import com.zephyr.data.protocol.dto.StatisticsDto;
import com.zephyr.data.protocol.request.StatisticRequest;
import com.zephyr.rating.services.StatisticService;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

import java.security.Principal;
import javax.validation.Valid;

@RestController
@AllArgsConstructor
@RequestMapping("/v1/statistic")
public class StatisticController {

    private StatisticService statisticService;

    @GetMapping(produces = MediaType.APPLICATION_STREAM_JSON_VALUE)
    public Flux<StatisticsDto> findStatisticsAndSubscribeForTask(@Valid StatisticRequest request, Principal principal) {
        return statisticService.findStatisticsAndSubscribeForTask(request.withPrincipal(principal));
    }
}
