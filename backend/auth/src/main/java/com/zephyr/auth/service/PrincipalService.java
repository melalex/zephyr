package com.zephyr.auth.service;

import java.security.Principal;

public interface PrincipalService {

    Principal extract(Principal principal);
}
