package io.javerse.repo;

import org.javers.spring.annotation.JaversSpringDataAuditable;
import org.springframework.data.repository.CrudRepository;

import io.javerse.domain.Store;

@JaversSpringDataAuditable
public interface StoreRepository extends CrudRepository<Store, Integer> {
}