package com.zephyr.mapping.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDto implements Serializable {
    private static final long serialVersionUID = -3485081063982508369L;

    private String firstName;
    private String lastName;
}
