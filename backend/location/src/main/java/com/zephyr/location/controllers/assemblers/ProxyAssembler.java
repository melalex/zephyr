package com.zephyr.location.controllers.assemblers;

import com.zephyr.data.resources.ProxyResource;
import com.zephyr.location.controllers.CountryController;
import com.zephyr.location.controllers.ProxyController;
import com.zephyr.location.services.dto.ProxyDto;
import com.zephyr.mapping.mappers.ExtendedMapper;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.mvc.IdentifiableResourceAssemblerSupport;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;

@Component
public class ProxyAssembler extends IdentifiableResourceAssemblerSupport<ProxyDto, ProxyResource> {
    private static final String COUNTRY_REL = "country";

    @Setter(onMethod = @__(@Autowired))
    private ExtendedMapper mapper;

    public ProxyAssembler() {
        super(ProxyController.class, ProxyResource.class);
    }

    @Override
    public ProxyResource toResource(ProxyDto entity) {
        ProxyResource resource = mapper.map(entity, ProxyResource.class);

        resource.add(linkTo(CountryController.class)
                .slash(entity.getCountryIso())
                .withRel(COUNTRY_REL));

        return resource;
    }
}