package com.zephyr.rating.support;

import com.zephyr.commons.interfaces.Assembler;
import com.zephyr.data.protocol.dto.SearchCriteriaDto;
import com.zephyr.data.protocol.dto.TaskDto;
import com.zephyr.data.protocol.request.StatisticRequest;
import com.zephyr.rating.cliensts.TaskServiceClient;
import com.zephyr.rating.domain.QueryCriteria;
import com.zephyr.rating.domain.RequestCriteria;
import lombok.AllArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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
        return t -> {
            var prototype = RequestCriteria.builder()
                    .url(t.getUrl())
                    .from(source.getFrom())
                    .to(source.getTo())
                    .build();

            return t.getSearchCriteria()
                    .stream()
                    .flatMap(criteriaFactoryFunction(prototype, t))
                    .collect(Collectors.toList());
        };
    }

    @NotNull
    private Function<SearchCriteriaDto, Stream<RequestCriteria>> criteriaFactoryFunction(RequestCriteria prototype,
                                                                                         TaskDto task) {
        return c -> task.getEngines().stream()
                .map(e -> prototype.toBuilder()
                        .engine(e.name())
                        .originalCriteriaId(c.getId())
                        .queryCriteria(mapper.map(c, QueryCriteria.class))
                        .build());
    }
}
