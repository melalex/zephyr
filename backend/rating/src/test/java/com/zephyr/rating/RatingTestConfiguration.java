package com.zephyr.rating;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import com.zephyr.commons.support.Profiles;
import com.zephyr.rating.cliensts.TaskServiceClient;
import com.zephyr.rating.data.TestDataLoader;
import com.zephyr.test.CommonTestData;
import com.zephyr.test.Tasks;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Profile;
import reactor.core.publisher.Mono;

@TestConfiguration
@ComponentScan(basePackageClasses = TestDataLoader.class)
public class RatingTestConfiguration {

    @Bean
    @Profile(Profiles.TEST)
    public TaskServiceClient agentServiceClient() {
        TaskServiceClient mock = mock(TaskServiceClient.class);
        when(mock.findByUserAndIdAsync(Tasks.SIMPLE_USER_ID, Tasks.SIMPLE_ID))
                .thenReturn(Mono.just(CommonTestData.tasks().simple()));
        return mock;
    }
}

