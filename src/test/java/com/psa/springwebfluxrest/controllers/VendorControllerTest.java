package com.psa.springwebfluxrest.controllers;

import com.psa.springwebfluxrest.domain.Vendor;
import com.psa.springwebfluxrest.repositories.VendorRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.reactivestreams.Publisher;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

/**
 * Created by pyaesoneaung at 08/04/2021
 */
class VendorControllerTest {

    WebTestClient webTestClient;
    VendorRepository vendorRepository;
    VendorController vendorController;

    @BeforeEach
    void setUp() throws Exception {
        vendorRepository = Mockito.mock(VendorRepository.class);
        vendorController = new VendorController(vendorRepository);
        webTestClient = WebTestClient.bindToController(vendorController).build();

    }

    @Test
    void testList() throws Exception {
        given(vendorRepository.findAll())
                .willReturn(Flux.just(Vendor.builder().firstName("Pyae").lastName("Sone").build(),
                        Vendor.builder().firstName("Pyae").lastName("Sone").build()));

        webTestClient.get().uri("/api/v1/vendors").exchange().expectBodyList(Vendor.class).hasSize(2);
    }

    @Test
    void testFindById() throws Exception {
        given(vendorRepository.findById("someId"))
                .willReturn(Mono.just(Vendor.builder().firstName("Pyae").lastName("Sone").build()));

        webTestClient.get().uri("/api/v1/vendors/someId").exchange().expectBodyList(Vendor.class);
    }

    @Test
    void testCreate() throws Exception{
        given(vendorRepository.saveAll(any(Publisher.class)))
                .willReturn(Flux.just(Vendor.builder().build()));

        Mono<Vendor> vendoToSavedMono = Mono.just(Vendor.builder().firstName("Pyae").lastName("Sone").build());

        webTestClient.post()
                .uri("/api/v1/vendors")
                .body(vendoToSavedMono,Vendor.class)
                .exchange()
                .expectStatus()
                .isCreated();
    }

    @Test
    void testUpdate() throws Exception {
        given(vendorRepository.save(any(Vendor.class)))
                .willReturn(Mono.just(Vendor.builder().build()));

        Mono<Vendor> vendoToSavedMono = Mono.just(Vendor.builder().firstName("Pyae").lastName("Sone").build());

        webTestClient.put()
                .uri("/api/v1/vendors/someId")
                .body(vendoToSavedMono,Vendor.class)
                .exchange()
                .expectStatus()
                .isOk();
    }

    @Test
    void testPatchWithChanges() throws Exception {

        given(vendorRepository.findById(anyString()))
                .willReturn(Mono.just(Vendor.builder().firstName("Pyae").build()));

        given(vendorRepository.save(any(Vendor.class)))
                .willReturn(Mono.just(Vendor.builder().build()));

        Mono<Vendor> vendoToSavedMono = Mono.just(Vendor.builder().firstName("Sone").build());

        webTestClient.patch()
                .uri("/api/v1/vendors/someId")
                .body(vendoToSavedMono,Vendor.class)
                .exchange()
                .expectStatus()
                .isOk();

        verify(vendorRepository).save(any());
    }

    @Test
    void testPatchNoChanges() throws Exception {

        given(vendorRepository.findById(anyString()))
                .willReturn(Mono.just(Vendor.builder().firstName("Pyae").build()));

        given(vendorRepository.save(any(Vendor.class)))
                .willReturn(Mono.just(Vendor.builder().build()));

        Mono<Vendor> vendoToSavedMono = Mono.just(Vendor.builder().firstName("Pyae").build());

        webTestClient.patch()
                .uri("/api/v1/vendors/someId")
                .body(vendoToSavedMono,Vendor.class)
                .exchange()
                .expectStatus()
                .isOk();

        verify(vendorRepository,never()).save(any());
    }
}