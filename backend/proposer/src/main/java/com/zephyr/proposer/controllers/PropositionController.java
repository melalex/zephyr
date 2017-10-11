package com.zephyr.proposer.controllers;

import com.zephyr.proposer.services.PropositionService;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PropositionController {

    @Setter(onMethod = @__(@Autowired))
    private PropositionService propositionService;
}
