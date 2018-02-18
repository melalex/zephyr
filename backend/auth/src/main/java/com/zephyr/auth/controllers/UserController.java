package com.zephyr.auth.controllers;

import com.zephyr.auth.service.PrincipalService;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
@RequestMapping("/v1/users")
public class UserController {

    @Setter(onMethod = @__(@Autowired))
    private PrincipalService principalService;

    @GetMapping("/current")
    public Principal current(Principal principal) {
        return principalService.extract(principal);
    }
}
