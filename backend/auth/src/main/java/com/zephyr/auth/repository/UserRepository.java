package com.zephyr.auth.repository;

import com.zephyr.auth.domain.User;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

public interface UserRepository extends ReactiveCrudRepository<User, String> {

}
