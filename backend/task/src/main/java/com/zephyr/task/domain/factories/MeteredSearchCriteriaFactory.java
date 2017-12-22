package com.zephyr.task.domain.factories;

import com.zephyr.task.domain.MeteredSearchCriteria;
import com.zephyr.task.domain.SearchCriteria;
import com.zephyr.task.services.dto.SearchCriteriaDto;
import lombok.Setter;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class MeteredSearchCriteriaFactory {

    @Setter(onMethod = @__(@Autowired))
    public ModelMapper modelMapper;

    public MeteredSearchCriteria create(SearchCriteria searchCriteria) {
        MeteredSearchCriteria meteredSearchCriteria = new MeteredSearchCriteria();

        meteredSearchCriteria.setSearchCriteria(searchCriteria);
        meteredSearchCriteria.setLastHit(LocalDateTime.now());
        meteredSearchCriteria.setHitsCount(1);

        return meteredSearchCriteria;
    }

    public Example<MeteredSearchCriteria> createExample(SearchCriteriaDto searchCriteria) {
        return createExample(modelMapper.map(searchCriteria, SearchCriteria.class));
    }

    public Example<MeteredSearchCriteria> createExample(SearchCriteria searchCriteria) {
        MeteredSearchCriteria meteredSearchCriteria = new MeteredSearchCriteria();

        meteredSearchCriteria.setSearchCriteria(searchCriteria);

        return Example.of(meteredSearchCriteria);
    }
}
