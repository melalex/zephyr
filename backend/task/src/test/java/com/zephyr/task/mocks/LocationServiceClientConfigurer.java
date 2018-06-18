package com.zephyr.task.mocks;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.startsWith;
import static org.mockito.Mockito.when;

import com.zephyr.task.clients.LocationServiceClient;
import com.zephyr.test.CommonTestData;
import com.zephyr.test.Countries;
import com.zephyr.test.Places;
import reactor.core.publisher.Flux;

import java.util.List;

public final class LocationServiceClientConfigurer {

    public static LocationServiceClient configure(LocationServiceClient mock) {
        var kiev = CommonTestData.places().kiev();
        var calgary = CommonTestData.places().calgary();

        when(mock.findByCountryIsoAndNameContains(eq(Countries.UA_ISO), startsWith(Places.KIEV_NAME)))
                .thenReturn(List.of(kiev));
        when(mock.findByCountryIsoAndNameContains(eq(Countries.CA_ISO), startsWith(Places.CALGARY_NAME)))
                .thenReturn(List.of(calgary));
        when(mock.findByCountryIsoAndNameContainsAsync(eq(Countries.UA_ISO), startsWith(Places.KIEV_NAME)))
                .thenReturn(Flux.just(kiev));
        when(mock.findByCountryIsoAndNameContainsAsync(eq(Countries.CA_ISO), startsWith(Places.CALGARY_NAME)))
                .thenReturn(Flux.just(calgary));

        return mock;
    }

}
