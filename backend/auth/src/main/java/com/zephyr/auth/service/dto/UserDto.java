package com.zephyr.auth.service.dto;

import lombok.Data;
import org.hibernate.validator.constraints.Email;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.Set;

@Data
public class UserDto implements UserDetails, Serializable {
    private static final long serialVersionUID = -6489437822547204208L;

    @NotNull
    @Size(min = 4, max = 60)
    private String username;

    @NotNull
    @Email
    private String email;

    @NotNull
    @Size(min = 4, max = 60)
    private String password;

    private Set<GrantedAuthority> authorities;

    private boolean emailConfirmed;
    private boolean enabled;

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }
}