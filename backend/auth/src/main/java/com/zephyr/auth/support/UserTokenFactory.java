package com.zephyr.auth.support;

import com.zephyr.auth.domain.User;
import com.zephyr.auth.domain.UserToken;
import lombok.SneakyThrows;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Base64;
import java.util.Set;
import java.util.UUID;

@Component
public class UserTokenFactory {

    public UserToken create(User user) {
        return UserToken.builder()
                .expiresIn(LocalDateTime.now().plusYears(1))
                .accessToken("bearer")
                .refreshToken(getToken(UUID.randomUUID().toString()))
                .accessToken(getToken(user.getUsername()))
                .scope(Set.of())
                .build();
    }

    @SneakyThrows
    private String getToken(String token) {
        return Base64.getEncoder().encodeToString(token.getBytes("UTF-8"));
    }
}
