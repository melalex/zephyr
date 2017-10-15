package com.zephyr.data.resources;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.hateoas.ResourceSupport;

@Data
@EqualsAndHashCode(callSuper = true)
public class LanguageResource extends ResourceSupport {
    private String iso;
    private String name;
}
