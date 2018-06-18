package com.zephyr.task;

import static org.mockito.Mockito.mock;

import com.zephyr.commons.interfaces.UidProvider;
import com.zephyr.commons.support.Profiles;
import com.zephyr.task.clients.AgentServiceClient;
import com.zephyr.task.clients.LocationServiceClient;
import com.zephyr.task.mocks.LocationServiceClientConfigurer;
import com.zephyr.test.mocks.TimeMachine;
import com.zephyr.test.mocks.UidProviderMock;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;

import java.time.Clock;

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
    @Profile(Profiles.TEST)
    public AgentServiceClient agentServiceClient() {
        return mock(AgentServiceClient.class);
    }

    @Bean
    @Primary
    @Profile(Profiles.TEST)
    public LocationServiceClient locationServiceClient() {
        return LocationServiceClientConfigurer.configure(mock(LocationServiceClient.class));
    }
}

