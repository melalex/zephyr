package com.zephyr.rating.services.transformers;

import com.zephyr.commons.interfaces.Transformer;
import com.zephyr.data.dto.TaskDto;
import com.zephyr.rating.domain.Rating;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Component;

@Component
public class TaskTransformer implements Transformer<TaskDto, Example<Rating>> {

    @Override
    public Example<Rating> transform(TaskDto source) {
        return null;
    }
}
