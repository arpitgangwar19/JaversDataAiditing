package io.javerse.repo;

import org.javers.spring.annotation.JaversAuditable;
import org.springframework.data.repository.CrudRepository;

import io.javerse.domain.Product;

public interface ProductRepository extends CrudRepository<Product, Integer> {
    @Override
    @JaversAuditable
    <S extends Product> S save(S s);
}