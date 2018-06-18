package com.zephyr.auth.service.impl;

import static java.lang.String.format;

import com.zephyr.auth.repository.UserRepository;
import com.zephyr.auth.wrapper.UserDetailsWrapper;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceAdapter implements UserDetailsService {

    @Setter(onMethod = @__(@Autowired))
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String name) throws UsernameNotFoundException {
        return userRepository.findByUsername(name)
                .map(UserDetailsWrapper::wrap)
                .orElseThrow(() -> new UsernameNotFoundException(format("User with username [ %s ] not found", name)));
    }
}
