package com.zephyr.task.controllers.assemblers;

import com.zephyr.task.controllers.DictionaryController;
import com.zephyr.task.controllers.resources.DictionaryResource;
import com.zephyr.task.services.dto.DictionaryDto;
import com.zephyr.mapping.mappers.ExtendedMapper;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.mvc.IdentifiableResourceAssemblerSupport;
import org.springframework.stereotype.Component;

@Component
public class DictionaryAssembler extends IdentifiableResourceAssemblerSupport<DictionaryDto, DictionaryResource> {

    @Setter(onMethod = @__(@Autowired))
    private ExtendedMapper mapper;

    public DictionaryAssembler() {
        super(DictionaryController.class, DictionaryResource.class);
    }

    @Override
    public DictionaryResource toResource(DictionaryDto entity) {
        return mapper.map(entity, DictionaryResource.class);
    }
}
