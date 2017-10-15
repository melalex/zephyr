package com.zephyr.location.controllers.assemblers;

import com.zephyr.data.resources.CountryResource;
import com.zephyr.location.controllers.CountryController;
import com.zephyr.location.services.dto.CountryDto;
import com.zephyr.mapping.mappers.ExtendedMapper;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.mvc.IdentifiableResourceAssemblerSupport;
import org.springframework.stereotype.Component;

@Component
public class CountryAssembler extends IdentifiableResourceAssemblerSupport<CountryDto, CountryResource> {

    @Setter(onMethod = @__(@Autowired))
    private ExtendedMapper mapper;

    public CountryAssembler() {
        super(CountryController.class, CountryResource.class);
    }

    @Override
    public CountryResource toResource(CountryDto entity) {
        return mapper.map(entity, CountryResource.class);
    }
}
