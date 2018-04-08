package com.zephyr.rating.business.converters;

import com.zephyr.commons.StreamUtils;
import com.zephyr.commons.support.Indexed;
import com.zephyr.data.internal.dto.SearchResultDto;
import com.zephyr.rating.core.domain.Request;
import lombok.Setter;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;
import reactor.util.function.Tuple2;
import reactor.util.function.Tuples;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class SearchResultConverter implements Converter<SearchResultDto, Tuple2<Request, List<Indexed<String>>>> {

    @Setter(onMethod = @__(@Autowired))
    private ModelMapper modelMapper;

    @Override
    public Tuple2<Request, List<Indexed<String>>> convert(@NonNull SearchResultDto source) {
        Request request = modelMapper.map(source, Request.class);

        List<Indexed<String>> links = StreamUtils.zipWithIndexes(source.getLinks(), source.getOffset())
                .collect(Collectors.toList());

        return Tuples.of(request, links);
    }
}
