package com.zephyr.rating.services.transformers;

import com.zephyr.commons.interfaces.Transformer;
import com.zephyr.data.dto.TaskDto;
import com.zephyr.rating.domain.Query;
import com.zephyr.rating.domain.RatingCriteria;
import lombok.Setter;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class TaskTransformer implements Transformer<TaskDto, Iterable<RatingCriteria>> {

    @Setter(onMethod = @__(@Autowired))
    private ModelMapper modelMapper;

    @Override
    public Iterable<RatingCriteria> transform(TaskDto source) {
        RatingCriteria prototype = modelMapper.map(source, RatingCriteria.class);

        return source.getSearchCriteria().stream()
                .map(c -> prototype.withQuery(modelMapper.map(c, Query.class)))
                .collect(Collectors.toList());
    }
}
