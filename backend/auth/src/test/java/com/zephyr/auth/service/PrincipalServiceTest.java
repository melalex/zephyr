package com.zephyr.auth.service;

import static org.junit.Assert.assertEquals;

import com.sun.security.auth.UserPrincipal;
import com.zephyr.auth.service.impl.PrincipalServiceImpl;
import org.junit.Test;

import java.security.Principal;

public class PrincipalServiceTest {

    private static final String PRINCIPAL_NAME = "test";

    private final Principal principal = new UserPrincipal(PRINCIPAL_NAME);

    private final PrincipalService testInstance = new PrincipalServiceImpl();

    @Test
    public void should() {
        assertEquals(principal, testInstance.extract(principal));
    }
}