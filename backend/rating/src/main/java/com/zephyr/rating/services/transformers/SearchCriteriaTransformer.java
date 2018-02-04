package com.zephyr.rating.services.transformers;

import com.zephyr.commons.interfaces.Transformer;
import com.zephyr.data.dto.SearchCriteriaDto;
import com.zephyr.rating.domain.Query;
import com.zephyr.rating.domain.Rating;
import lombok.Setter;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class SearchCriteriaTransformer implements Transformer<SearchCriteriaDto, Rating> {

    @Setter(onMethod = @__(@Autowired))
    private ModelMapper modelMapper;

    @Override
    public Rating transform(SearchCriteriaDto source) {
        return Rating.builder()
                .query(modelMapper.map(source, Query.class))
                .build();
    }
}
