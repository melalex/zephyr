package com.zephyr.auth.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.zephyr.auth.service.dto.UserDto;
import com.zephyr.auth.service.PrincipalService;
import com.zephyr.auth.service.UserService;
import com.sun.security.auth.UserPrincipal;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import reactor.core.publisher.Mono;

import java.security.Principal;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(UserController.class)
public class UserControllerTest {
    private static final String VALID_USERNAME = "VALID_USERNAME";
    private static final String VALID_EMAIL = "VALID_EMAIL";
    private static final String VALID_PASSWORD = "VALID_PASSWORD";

    private static final String INVALID_USERNAME = "1";
    private static final String INVALID_EMAIL = "1";
    private static final String INVALID_PASSWORD = "1";

    private static final String PRINCIPAL_NAME = "test";

    private final ObjectMapper mapper = new ObjectMapper();
    private final Principal principal = new UserPrincipal(PRINCIPAL_NAME);

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @MockBean
    private PrincipalService principalService;

    @Before
    public void setUp() {
        when(userService.createCustomer(any())).thenReturn(Mono.just(new UserDto()));
        when(principalService.extract(principal)).thenReturn(principal);
    }

    @Test
    public void shouldCreateNewUser() throws Exception {
        UserDto user = new UserDto();

        user.setUsername(VALID_USERNAME);
        user.setEmail(VALID_EMAIL);
        user.setPassword(VALID_PASSWORD);

        String json = mapper.writeValueAsString(user);

        mockMvc.perform(post("/v1/users").contentType(MediaType.APPLICATION_JSON).content(json))
                .andExpect(status().isOk());
    }

    @Test
    public void shouldFailWhenUserIsNotValid() throws Exception {
        UserDto user = new UserDto();

        user.setUsername(INVALID_USERNAME);
        user.setEmail(INVALID_EMAIL);
        user.setPassword(INVALID_PASSWORD);

        String json = mapper.writeValueAsString(user);

        mockMvc.perform(post("/v1/users").contentType(MediaType.APPLICATION_JSON).content(json))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void shouldFailWhenNoUser() throws Exception {
        mockMvc.perform(post("/v1/users"))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void shouldReturnCurrentUser() throws Exception {
        mockMvc.perform(get("/v1/users/current").principal(principal))
                .andExpect(jsonPath("$.name").value(PRINCIPAL_NAME))
                .andExpect(status().isOk());
    }
}