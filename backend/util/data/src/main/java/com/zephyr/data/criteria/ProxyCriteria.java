package com.zephyr.data.criteria;

import com.zephyr.data.enums.Protocol;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.Set;

@Data
public class ProxyCriteria {

    @NotNull
    private String countryIso;
    private Set<Protocol> protocols;
}
