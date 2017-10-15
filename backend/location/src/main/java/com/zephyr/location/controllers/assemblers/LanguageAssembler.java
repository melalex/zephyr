package com.zephyr.location.controllers.assemblers;

import com.zephyr.data.resources.LanguageResource;
import com.zephyr.location.controllers.LanguageController;
import com.zephyr.location.services.dto.LanguageDto;
import com.zephyr.mapping.mappers.ExtendedMapper;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.mvc.IdentifiableResourceAssemblerSupport;
import org.springframework.stereotype.Component;

@Component
public class LanguageAssembler extends IdentifiableResourceAssemblerSupport<LanguageDto, LanguageResource> {

    @Setter(onMethod = @__(@Autowired))
    private ExtendedMapper mapper;

    public LanguageAssembler() {
        super(LanguageController.class, LanguageResource.class);
    }

    @Override
    public LanguageResource toResource(LanguageDto entity) {
        return mapper.map(entity, LanguageResource.class);
    }
}
