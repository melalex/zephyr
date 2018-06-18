package com.zephyr.location.controllers;

import static org.junit.Assert.assertEquals;

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
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.RequestEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;
import java.util.Map;
import java.util.Set;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class CountryControllerIntegrationTest {

    @Autowired
    private TestRestTemplate rest;

    @Autowired
    private CountryRepository countryRepository;

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
    public void shouldFindByIso() {
        var actual =
                rest.getForEntity("/v1/countries/{iso}", CountryDto.class, Map.of("iso", Countries.UA_ISO));

        assertEquals(HttpStatus.OK, actual.getStatusCode());
        assertEquals(CommonTestData.countries().ukraine(), actual.getBody());
    }

    @Test
    public void shouldNotFindByIso() {
        var actual =
                rest.getForEntity("/v1/countries/{iso}", CountryDto.class, Map.of("iso", "INVALID"));

        assertEquals(HttpStatus.NOT_FOUND, actual.getStatusCode());
    }

    @Test
    @SuppressWarnings("Convert2Diamond")
    public void shouldFind() {
        var requestEntity = RequestEntity
                .get(UriComponentsBuilder.fromUriString("/v1/countries").queryParam("name", "Uk").build().toUri())
                .build();

        var actual = rest.exchange(
                requestEntity,
                new ParameterizedTypeReference<Set<CountryDto>>() {
                }
        );

        assertEquals(HttpStatus.OK, actual.getStatusCode());
        assertEquals(Set.of(CommonTestData.countries().ukraine()), actual.getBody());
    }

    @Test
    @SuppressWarnings("Convert2Diamond")
    public void shouldFindWithNullName() {
        var expected = Set.of(
                CommonTestData.countries().ukraine(),
                CommonTestData.countries().canada()
        );

        var actual = rest.exchange(
                "/v1/countries",
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<Set<CountryDto>>() {
                }
        );

        assertEquals(HttpStatus.OK, actual.getStatusCode());
        assertEquals(expected, actual.getBody());
    }
}