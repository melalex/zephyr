package com.zephyr.rating.support;

import com.zephyr.commons.StreamUtils;
import com.zephyr.data.internal.dto.SearchResultDto;
import com.zephyr.rating.domain.Request;
import com.zephyr.rating.domain.vo.SearchResultVo;
import lombok.Setter;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class SearchResultConverter implements Converter<SearchResultDto, SearchResultVo> {

    @Setter(onMethod = @__(@Autowired))
    private ModelMapper modelMapper;

    @Override
    public SearchResultVo convert(@NonNull SearchResultDto source) {
        var request = modelMapper.map(source, Request.class);

        var links = StreamUtils.zipWithIndexes(source.getLinks(), source.getOffset())
                .collect(Collectors.toList());

        return SearchResultVo.of(request, links);
    }
}
