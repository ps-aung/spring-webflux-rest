package com.psa.springwebfluxrest.bootstrap;

import com.psa.springwebfluxrest.domain.Category;
import com.psa.springwebfluxrest.domain.Vendor;
import com.psa.springwebfluxrest.repositories.CategoryRepository;
import com.psa.springwebfluxrest.repositories.VendorRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

/**
 * Created by pyaesoneaung at 08/04/2021
 */
@Component
public class Bootstrap implements CommandLineRunner {

    private CategoryRepository categoryRepository;
    private VendorRepository vendorRepository;

    public Bootstrap(CategoryRepository categoryRepository, VendorRepository vendorRepository) {
        this.categoryRepository = categoryRepository;
        this.vendorRepository = vendorRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        loadCategories();
        loadVendors();
    }

    private void loadCategories(){
        if(categoryRepository.count().block() == 0){
            //load data
            System.out.println("#### LOADING DATA ON BOOTSTRAP #####");

            categoryRepository.save(Category.builder()
                    .description("Fruits").build()).block();

            categoryRepository.save(Category.builder()
                    .description("Nuts").build()).block();

            categoryRepository.save(Category.builder()
                    .description("Breads").build()).block();

            categoryRepository.save(Category.builder()
                    .description("Meats").build()).block();

            categoryRepository.save(Category.builder()
                    .description("Eggs").build()).block();

            System.out.println("Loaded Categories: " + categoryRepository.count().block());

            System.out.println("Loaded Categories: " + categoryRepository.count().block());

        }
    }

    private void loadVendors() {
        if(vendorRepository.count().block() == 0){

            vendorRepository.save(Vendor.builder()
                    .firstName("Pyae")
                    .lastName("Sone").build()).block();

            vendorRepository.save(Vendor.builder()
                    .firstName("Thet")
                    .lastName("Hnin").build()).block();

            vendorRepository.save(Vendor.builder()
                    .firstName("Zee")
                    .lastName("Kwet").build()).block();

            vendorRepository.save(Vendor.builder()
                    .firstName("Eain")
                    .lastName("Myaung").build()).block();

            vendorRepository.save(Vendor.builder()
                    .firstName("Spring")
                    .lastName("Boot").build()).block();

            System.out.println("Loaded Vendors: " + vendorRepository.count().block());
        }
    }
}
