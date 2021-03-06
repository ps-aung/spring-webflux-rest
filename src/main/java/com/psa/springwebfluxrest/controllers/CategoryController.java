package com.psa.springwebfluxrest.controllers;

import com.psa.springwebfluxrest.domain.Category;
import com.psa.springwebfluxrest.repositories.CategoryRepository;
import org.reactivestreams.Publisher;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * Created by pyaesoneaung at 08/04/2021
 */
@RestController
public class CategoryController {

    private final CategoryRepository categoryRepository;

    public CategoryController(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @GetMapping("/api/v1/categories")
    Flux<Category> list(){
        return categoryRepository.findAll();
    }

    @GetMapping("/api/v1/categories/{id}")
    Mono<Category> getById(@PathVariable String id){
        return categoryRepository.findById(id);
    }

    @PostMapping("/api/v1/categories")
    @ResponseStatus(HttpStatus.CREATED)
    Mono<Void> create(@RequestBody Publisher<Category> categoryPublisher){
        return categoryRepository.saveAll(categoryPublisher).then();
    }

    @PutMapping("/api/v1/categories/{id}")
    Mono<Category> update(@PathVariable String id,@RequestBody Category category){
        category.setId(id);
        return categoryRepository.save(category);
    }

    @PatchMapping("/api/v1/categories/{id}")
    Mono<Category> patch(@PathVariable String id,@RequestBody Category category){
        Category foundCategory = categoryRepository.findById(id).block();

        if(!foundCategory.getDescription().equals(category.getDescription())){
            foundCategory.setDescription(category.getDescription());
            return categoryRepository.save(foundCategory);
        }
        return Mono.just(foundCategory);
    }

}
