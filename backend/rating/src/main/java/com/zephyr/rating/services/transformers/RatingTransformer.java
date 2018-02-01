package com.zephyr.rating.services.transformers;

import com.zephyr.commons.interfaces.Transformer;
import com.zephyr.rating.domain.Rating;
import com.zephyr.rating.services.dto.RatingDto;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class RatingTransformer implements Transformer<List<Rating>, RatingDto> {

    @Override
    public RatingDto transform(List<Rating> source) {
        return null;
    }
}
