package com.zephyr.rating.services.converters;

import com.zephyr.data.protocol.dto.TaskDto;
import com.zephyr.rating.domain.Query;
import com.zephyr.rating.domain.RequestCriteria;
import lombok.Setter;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class TaskTransformer implements Converter<TaskDto, Iterable<RequestCriteria>> {

    @Setter(onMethod = @__(@Autowired))
    private ModelMapper modelMapper;

    @Override
    public Iterable<RequestCriteria> convert(@NonNull TaskDto source) {
        RequestCriteria prototype = modelMapper.map(source, RequestCriteria.class);

        return source.getSearchCriteria().stream()
                .map(c -> prototype.withQuery(modelMapper.map(c, Query.class)))
                .collect(Collectors.toList());
    }
}
