package com.zephyr.rating.services.transformers;

import com.zephyr.commons.StreamUtils;
import com.zephyr.commons.interfaces.Transformer;
import com.zephyr.data.internal.dto.SearchResultDto;
import com.zephyr.rating.domain.Rating;
import com.zephyr.rating.domain.Request;
import lombok.Setter;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class SearchResultTransformer implements Transformer<SearchResultDto, List<Rating>> {

    @Setter(onMethod = @__(@Autowired))
    private ModelMapper modelMapper;

    @Override
    public List<Rating> transform(SearchResultDto source) {
        Request request = modelMapper.map(source, Request.class);

        return StreamUtils.zipWithIndexes(source.getLinks(), source.getOffset())
                .map(p -> new Rating(request, p.getKey(), p.getValue()))
                .collect(Collectors.toList());
    }
}
