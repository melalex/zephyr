package com.zephyr.auth.controller;

import com.zephyr.auth.dto.UserDto;
import com.zephyr.auth.service.PrincipalService;
import com.zephyr.auth.service.UserService;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import javax.validation.Valid;
import java.security.Principal;

@RestController
@RequestMapping("/v1/users")
public class UserController {

    @Setter(onMethod = @__(@Autowired))
    private UserService userService;

    @Setter(onMethod = @__(@Autowired))
    private PrincipalService principalService;

    @RequestMapping(value = "/current", method = RequestMethod.GET)
    public Principal current(Principal principal) {
        return principalService.extract(principal);
    }

    @PreAuthorize("#oauth2.hasScope('server')")
    @RequestMapping(value = "/customer", method = RequestMethod.POST)
    public Mono<Void> createCustomer(@Valid UserDto user) {
        return userService.createCustomer(user).then();
    }
}
