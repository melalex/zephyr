package com.zephyr.location.controllers;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.zephyr.location.data.LocationTestData;
import com.zephyr.location.repositories.PlaceRepository;
import com.zephyr.test.CommonTestData;
import com.zephyr.test.Countries;
import com.zephyr.test.Places;
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
public class PlaceControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private PlaceRepository placeRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @Before
    public void setUp() {
        placeRepository.saveAll(List.of(
                LocationTestData.places().kiev(),
                LocationTestData.places().calgary())
        );
    }

    @After
    public void tearDown() {
        placeRepository.deleteAll();
    }

    @Test
    public void shouldFindById() throws Exception {
        mockMvc.perform(get("/v1/places/{id}", Places.KIEV_ID))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(CommonTestData.places().kiev())));
    }

    @Test
    public void shouldNotFindById() throws Exception {
        mockMvc.perform(get("/v1/places/{id}", "INVALID"))
                .andExpect(status().isNotFound());
    }

    @Test
    public void shouldFindAllByCountryIsoAndNameContains() throws Exception {
        mockMvc.perform(get("/v1/places").param("iso", Countries.CA_ISO).param("name", "cal"))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(Set.of(CommonTestData.places().calgary()))));
    }
}