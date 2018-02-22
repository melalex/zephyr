package com.zephyr.rating.services.converters;

import com.zephyr.commons.StreamUtils;
import com.zephyr.data.internal.dto.SearchResultDto;
import com.zephyr.rating.domain.Rating;
import com.zephyr.rating.domain.Request;
import lombok.Setter;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class SearchResultTransformer implements Converter<SearchResultDto, List<Rating>> {

    @Setter(onMethod = @__(@Autowired))
    private ModelMapper modelMapper;

    @Override
    public List<Rating> convert(@NonNull SearchResultDto source) {
        Request request = modelMapper.map(source, Request.class);

        return StreamUtils.zipWithIndexes(source.getLinks(), source.getOffset())
                .map(p -> new Rating(request, p.getIndex(), p.getElement()))
                .collect(Collectors.toList());
    }
}
