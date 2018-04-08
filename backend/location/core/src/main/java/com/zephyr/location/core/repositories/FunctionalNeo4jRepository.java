package com.zephyr.location.core.repositories;

import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.repository.NoRepositoryBean;

import java.io.Serializable;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

@NoRepositoryBean
public interface FunctionalNeo4jRepository<T, ID extends Serializable> extends Neo4jRepository<T, ID> {

    default Stream<T> findAllStream() {
        return StreamSupport.stream(findAll().spliterator(), false);
    }

    default Stream<T> findAllStream(int depth) {
        return StreamSupport.stream(findAll(depth).spliterator(), false);
    }
}
