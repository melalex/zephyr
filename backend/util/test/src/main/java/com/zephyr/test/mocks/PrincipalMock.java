package com.zephyr.test.mocks;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import com.zephyr.test.Tasks;
import lombok.experimental.UtilityClass;

import java.security.Principal;

@UtilityClass
public class PrincipalMock {

    public static final String USER1 = "USER1";

    public Principal of(String name) {
        Principal mock = mock(Principal.class);
        when(mock.getName()).thenReturn(name);
        return mock;
    }

    public Principal simple() {
        return of(Tasks.SIMPLE_NAME);
    }

    public Principal user1() {
        return of(USER1);
    }
}
