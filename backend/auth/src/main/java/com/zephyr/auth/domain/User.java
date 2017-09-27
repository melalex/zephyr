package com.zephyr.auth.domain;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;
import java.util.Set;

@Data
@Document(collection = "user")
public class User implements Serializable {
    private static final long serialVersionUID = 5222158829992191818L;

    @Id
    private String username;

    private String email;

    private String password;

    private Set<Role> roles;

    private boolean emailConfirmed;
    private boolean enabled;

    public enum Role {
        ADMIN,
        CUSTOMER
    }
}
