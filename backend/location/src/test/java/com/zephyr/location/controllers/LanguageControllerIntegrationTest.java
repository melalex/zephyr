package com.zephyr.location.controllers;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.zephyr.data.protocol.dto.LanguageDto;
import com.zephyr.location.data.LocationTestData;
import com.zephyr.location.repositories.LanguageRepository;
import com.zephyr.test.CommonTestData;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.Set;

@SpringBootTest
@AutoConfigureMockMvc
@RunWith(SpringRunner.class)
public class LanguageControllerIntegrationTest {

    @Autowired
    private LanguageRepository languageRepository;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Before
    public void setUp() {
        languageRepository.saveAll(List.of(
                LocationTestData.languages().ukrainian(),
                LocationTestData.languages().russian(),
                LocationTestData.languages().english()
        ));
    }

    @After
    public void tearDown() {
        languageRepository.deleteAll();
    }

    @Test
    public void shouldFindAll() throws Exception {
        Set<LanguageDto> expected = Set.of(
                CommonTestData.languages().ukrainian(),
                CommonTestData.languages().russian(),
                CommonTestData.languages().english()
        );
        mockMvc.perform(get("/v1/languages"))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(expected)));
    }
}