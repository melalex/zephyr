package com.zephyr.dictionary.controllers.assemblers;

import com.zephyr.dictionary.controllers.DictionaryController;
import com.zephyr.dictionary.controllers.resources.DictionaryResource;
import com.zephyr.dictionary.services.dto.DictionaryDto;
import com.zephyr.mapping.mappers.ExtendedMapper;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityLinks;
import org.springframework.hateoas.mvc.ResourceAssemblerSupport;
import org.springframework.stereotype.Component;

@Component
public class DictionaryAssembler extends ResourceAssemblerSupport<DictionaryDto, DictionaryResource> {

    @Setter(onMethod = @__(@Autowired))
    private ExtendedMapper extendedMapper;

    @Setter(onMethod = @__(@Autowired))
    private EntityLinks entityLinks;

    public DictionaryAssembler() {
        super(DictionaryController.class, DictionaryResource.class);
    }

    @Override
    public DictionaryResource toResource(DictionaryDto entity) {
        DictionaryResource resource = extendedMapper.map(entity, DictionaryResource.class);

        resource.add(entityLinks.linkToSingleResource(DictionaryResource.class, entity.getId())
                .withSelfRel());

        return resource;
    }
}
