package com.zephyr.test.mocks;

import com.zephyr.test.Tasks;
import lombok.Value;
import lombok.experimental.UtilityClass;

import java.security.Principal;
import javax.security.auth.Subject;

@UtilityClass
public class PrincipalMock {

    public static final String USER1 = "USER1";

    public Principal of(String name) {
        return new TestPrincipal(name);
    }

    public Principal simple() {
        return of(Tasks.SIMPLE_NAME);
    }

    public Principal user1() {
        return of(USER1);
    }

    @Value
    private static class TestPrincipal implements Principal {

        private String name;

        @Override
        public boolean implies(Subject subject) {
            return true;
        }
    }

}
