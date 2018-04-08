package com.zephyr.agent.core.repositories;

import com.zephyr.agent.core.domain.UserAgent;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Flux;

public interface UserAgentRepository extends ReactiveMongoRepository<UserAgent, String> {

    Flux<UserAgent> findAllByDeviceAndOsNameAndBrowserName(String device, String osName, String browserName);
}
