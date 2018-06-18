package com.zephyr.location.controllers;

import static org.junit.Assert.assertEquals;

import com.zephyr.data.protocol.dto.PlaceDto;
import com.zephyr.location.data.LocationTestData;
import com.zephyr.location.domain.Place;
import com.zephyr.location.repositories.PlaceRepository;
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
import org.springframework.http.HttpStatus;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.Map;
import java.util.Set;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class PlaceControllerIntegrationTest {

    @Autowired
    private PlaceRepository placeRepository;

    @Autowired
    private TestRestTemplate rest;

    private Place kiev;
    private Place calgary;

    @Before
    public void setUp() {
        Place kievData = LocationTestData.places().kiev().withId(null);
        Place calgaryData = LocationTestData.places().calgary().withId(null);
        kievData.getParent().setId(null);
        calgaryData.getParent().setId(null);

        kiev = placeRepository.save(kievData);
        calgary = placeRepository.save(calgaryData);
    }

    @After
    public void tearDown() {
        placeRepository.deleteAll();
    }

    @Test
    public void shouldFindById() {
        ResponseEntity<PlaceDto> actual =
                rest.getForEntity("/v1/places/{id}", PlaceDto.class, Map.of("id", kiev.getId()));

        PlaceDto expected = CommonTestData.places().kiev();
        expected.setId(kiev.getId());
        expected.setParent(kiev.getParent().getId());

        assertEquals(HttpStatus.OK, actual.getStatusCode());
        assertEquals(expected, actual.getBody());
    }

    @Test
    public void shouldNotFindById() {
        ResponseEntity<PlaceDto> actual =
                rest.getForEntity("/v1/places/{id}", PlaceDto.class, Map.of("id", Long.MAX_VALUE));

        assertEquals(HttpStatus.NOT_FOUND, actual.getStatusCode());
    }

    @Test
    @SuppressWarnings("Convert2Diamond")
    public void shouldFindAllByCountryIsoAndNameContains() {
        URI uri = UriComponentsBuilder.fromUriString("/v1/places")
                .queryParam("iso", Countries.CA_ISO)
                .queryParam("name", "Cal")
                .build()
                .toUri();

        RequestEntity<Void> requestEntity = RequestEntity.get(uri)
                .build();

        ResponseEntity<Set<PlaceDto>> actual = rest.exchange(
                requestEntity,
                new ParameterizedTypeReference<Set<PlaceDto>>() {
                }
        );

        PlaceDto expected = CommonTestData.places().calgary();
        expected.setId(calgary.getId());
        expected.setParent(calgary.getParent().getId());

        assertEquals(HttpStatus.OK, actual.getStatusCode());
        assertEquals(Set.of(expected), actual.getBody());
    }

    @Test
    @SuppressWarnings("Convert2Diamond")
    public void shouldGetByCanonicalName() {
        URI uri = UriComponentsBuilder.fromUriString("/v1/places/canonical")
                .queryParam("name", "Cal")
                .build()
                .toUri();

        RequestEntity<Void> requestEntity = RequestEntity.get(uri)
                .build();

        ResponseEntity<Set<PlaceDto>> actual = rest.exchange(
                requestEntity,
                new ParameterizedTypeReference<Set<PlaceDto>>() {
                }
        );

        PlaceDto expected = CommonTestData.places().calgary();
        expected.setId(calgary.getId());
        expected.setParent(calgary.getParent().getId());

        assertEquals(HttpStatus.OK, actual.getStatusCode());
        assertEquals(Set.of(expected), actual.getBody());
    }
}