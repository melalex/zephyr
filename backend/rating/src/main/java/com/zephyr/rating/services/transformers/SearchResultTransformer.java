package com.zephyr.rating.services.transformers;

import com.zephyr.commons.StreamUtils;
import com.zephyr.commons.interfaces.Transformer;
import com.zephyr.data.dto.SearchResultDto;
import com.zephyr.rating.domain.Rating;
import lombok.Setter;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class SearchResultTransformer implements Transformer<SearchResultDto, Iterable<Rating>> {

    @Setter(onMethod = @__(@Autowired))
    private ModelMapper modelMapper;

    @Override
    public Iterable<Rating> transform(SearchResultDto source) {
        Rating prototype = modelMapper.map(source, Rating.class);
        return StreamUtils.zipWithIndexes(source.getLinks(), source.getOffset())
                .map(p -> prototype.withLinkAndPosition(p.getRight(), p.getLeft()))
                .collect(Collectors.toList());
    }
}
