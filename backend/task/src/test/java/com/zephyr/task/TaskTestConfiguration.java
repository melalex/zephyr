package com.zephyr.task;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.startsWith;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import com.zephyr.commons.interfaces.UidProvider;
import com.zephyr.task.clients.AgentServiceClient;
import com.zephyr.task.clients.LocationServiceClient;
import com.zephyr.test.CommonTestData;
import com.zephyr.test.Countries;
import com.zephyr.test.Places;
import com.zephyr.test.mocks.TimeMachine;
import com.zephyr.test.mocks.UidProviderMock;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;

import java.time.Clock;
import java.util.List;

@TestConfiguration
public class TaskTestConfiguration {

    @Bean
    @Primary
    public UidProvider uidProvider() {
        return UidProviderMock.of();
    }

    @Bean
    @Primary
    public TimeMachine timeMachine() {
        return TimeMachine.create();
    }

    @Bean
    @Primary
    public Clock clock() {
        return timeMachine().clock();
    }

    @Bean
    @Primary
    public AgentServiceClient agentServiceClient() {
        return mock(AgentServiceClient.class);
    }

    @Bean
    @Primary
    public LocationServiceClient locationServiceClient() {
        LocationServiceClient mock = mock(LocationServiceClient.class);
        when(mock.findByCountryIsoAndNameContains(eq(Countries.UA_ISO), startsWith(Places.KIEV_NAME)))
                .thenReturn(List.of(CommonTestData.places().kiev()));
        when(mock.findByCountryIsoAndNameContains(eq(Countries.CA_ISO), startsWith(Places.CALGARY_NAME)))
                .thenReturn(List.of(CommonTestData.places().calgary()));
        return mock;
    }
}

