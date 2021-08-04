package com.psa.springwebfluxrest.repositories;

import com.psa.springwebfluxrest.domain.Category;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

/**
 * Created by pyaesoneaung at 08/04/2021
 */
public interface CategoryRepository extends ReactiveMongoRepository<Category, String> {
}
