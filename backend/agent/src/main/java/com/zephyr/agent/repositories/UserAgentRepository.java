package com.zephyr.agent.repositories;

import com.zephyr.agent.domain.UserAgent;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface UserAgentRepository extends ReactiveMongoRepository<UserAgent, String> {

}
