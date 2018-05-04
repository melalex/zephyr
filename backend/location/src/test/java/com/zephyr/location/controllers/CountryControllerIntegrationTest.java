package com.zephyr.location.controllers;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.zephyr.data.protocol.dto.CountryDto;
import com.zephyr.location.data.LocationTestData;
import com.zephyr.location.repositories.CountryRepository;
import com.zephyr.test.CommonTestData;
import com.zephyr.test.Countries;
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
public class CountryControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private CountryRepository countryRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @Before
    public void setUp() {
        countryRepository.saveAll(List.of(
                LocationTestData.countries().ukraine(),
                LocationTestData.countries().canada()
        ));
    }

    @After
    public void tearDown() {
        countryRepository.deleteAll();
    }

    @Test
    public void shouldFindByIso() throws Exception {
        mockMvc.perform(get("/v1/countries/{iso}", Countries.UA_ISO))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(CommonTestData.countries().ukraine())));
    }

    @Test
    public void shouldNotFindByIso() throws Exception {
        mockMvc.perform(get("/v1/countries/{iso}", "INVALID"))
                .andExpect(status().isNotFound());
    }

    @Test
    public void shouldFind() throws Exception {
        Set<CountryDto> expected = Set.of(CommonTestData.countries().ukraine());
        mockMvc.perform(get("/v1/countries").param("name", "uk"))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(expected)));
    }

    @Test
    public void shouldFindWithNullName() throws Exception {
        Set<CountryDto> expected = Set.of(
                CommonTestData.countries().ukraine(),
                CommonTestData.countries().canada()
        );
        mockMvc.perform(get("/v1/countries").param("name", "uk"))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(expected)));
    }
}