package com.zephyr.data.protocol.dto;

import com.zephyr.validation.FieldMatch;
import lombok.Data;

import javax.validation.constraints.Size;

@Data
@FieldMatch(first = "password", second = "repeatPassword")
public class CreateUserDto {

    @Size(min = 4, max = 40)
    private String username;

    @Size(min = 4, max = 40)
    private String password;
    private String repeatPassword;
}
