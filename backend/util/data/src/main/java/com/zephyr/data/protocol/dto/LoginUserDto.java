package com.zephyr.data.protocol.dto;

import lombok.Data;
import lombok.NonNull;

@Data
public class LoginUserDto {

    @NonNull
    private String username;

    @NonNull
    private String password;
}
