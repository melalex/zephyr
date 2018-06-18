package com.zephyr.location.controllers;

import static org.junit.Assert.assertEquals;

import com.zephyr.data.protocol.dto.LanguageDto;
import com.zephyr.location.data.LocationTestData;
import com.zephyr.location.repositories.LanguageRepository;
import com.zephyr.test.CommonTestData;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpStatus;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;
import java.util.Set;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class LanguageControllerIntegrationTest {

    @Autowired
    private LanguageRepository languageRepository;

    @Autowired
    private TestRestTemplate rest;

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
    @SuppressWarnings("Convert2Diamond")
    public void shouldFindAll() {
        Set<LanguageDto> expected = Set.of(
                CommonTestData.languages().ukrainian(),
                CommonTestData.languages().russian(),
                CommonTestData.languages().english()
        );

        RequestEntity<Void> requestEntity = RequestEntity
                .get(UriComponentsBuilder.fromUriString("/v1/languages").build().toUri())
                .build();

        ResponseEntity<Set<LanguageDto>> actual = rest.exchange(
                requestEntity,
                new ParameterizedTypeReference<Set<LanguageDto>>() { }
        );

        assertEquals(HttpStatus.OK, actual.getStatusCode());
        assertEquals(expected, actual.getBody());
    }
}