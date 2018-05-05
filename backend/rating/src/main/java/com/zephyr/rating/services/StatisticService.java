package com.zephyr.rating.services;

import com.zephyr.data.protocol.dto.StatisticsDto;
import com.zephyr.data.protocol.request.StatisticRequest;
import reactor.core.publisher.Flux;

public interface StatisticService {

    Flux<StatisticsDto> findStatisticsAndSubscribeForTask(StatisticRequest request);
}
