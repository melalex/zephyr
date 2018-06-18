package com.zephyr.auth.domain;

import lombok.Builder;
import lombok.Value;

import java.time.LocalDateTime;
import java.util.Set;

@Value
@Builder
public class UserToken {

    private String accessToken;
    private String tokenType;
    private String refreshToken;
    private LocalDateTime expiresIn;
    private Set<String> scope;
}
