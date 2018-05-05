package com.zephyr.rating.support;

import com.zephyr.commons.interfaces.Assembler;
import com.zephyr.data.protocol.dto.SearchCriteriaDto;
import com.zephyr.data.protocol.dto.TaskDto;
import com.zephyr.data.protocol.request.StatisticRequest;
import com.zephyr.rating.cliensts.TaskServiceClient;
import com.zephyr.rating.domain.Query;
import com.zephyr.rating.domain.RequestCriteria;
import lombok.AllArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
@AllArgsConstructor
public class RequestCriteriaAssembler implements Assembler<StatisticRequest, List<RequestCriteria>> {

    private ModelMapper mapper;
    private TaskServiceClient taskServiceClient;

    @Override
    public Mono<List<RequestCriteria>> assemble(StatisticRequest source) {
        return taskServiceClient.findByUserAndIdAsync(source.getUserId(), source.getTaskId())
                .map(toRequestCriteria(source));
    }

    private Function<TaskDto, List<RequestCriteria>> toRequestCriteria(StatisticRequest source) {
        RequestCriteria prototype = mapper.map(source, RequestCriteria.class);
        prototype.setFrom(source.getFrom());
        prototype.setTo(source.getTo());

        return t -> t.getSearchCriteria().stream()
                .map(criteriaFactoryFunction(prototype))
                .collect(Collectors.toList());
    }

    @NotNull
    private Function<SearchCriteriaDto, RequestCriteria> criteriaFactoryFunction(RequestCriteria prototype) {
        return c -> prototype.toBuilder()
                .originalCriteriaId(c.getId())
                .query(mapper.map(c, Query.class))
                .build();
    }
}
