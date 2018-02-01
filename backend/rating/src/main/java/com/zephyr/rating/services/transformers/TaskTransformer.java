package com.zephyr.rating.services.transformers;

import com.zephyr.commons.interfaces.Transformer;
import com.zephyr.data.dto.SearchCriteriaDto;
import com.zephyr.rating.domain.Rating;
import lombok.Setter;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Component;

@Component
public class TaskTransformer implements Transformer<SearchCriteriaDto, Example<Rating>> {

    @Setter(onMethod = @__(@Autowired))
    private ModelMapper modelMapper;

    @Override
    public Example<Rating> transform(SearchCriteriaDto source) {
        Rating rating = Rating.builder()
                .query(modelMapper.map(source, Rating.Query.class))
                .build();

        return Example.of(rating);
    }
}
