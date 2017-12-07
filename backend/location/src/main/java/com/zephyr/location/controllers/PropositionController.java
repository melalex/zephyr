package com.zephyr.location.controllers;

import com.zephyr.location.services.PropositionService;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PropositionController {

    @Setter(onMethod = @__(@Autowired))
    private PropositionService propositionService;
}
