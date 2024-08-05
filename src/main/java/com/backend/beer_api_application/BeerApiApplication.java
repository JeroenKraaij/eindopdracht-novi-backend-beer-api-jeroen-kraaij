package com.backend.beer_api_application;

import com.backend.beer_api_application.models.Customer;
import com.backend.beer_api_application.repositories.CustomerRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.List;

@SpringBootApplication
public class BeerApiApplication {
    public static void main(String[] args) {
        SpringApplication.run(BeerApiApplication.class, args);
    }

    @Bean
    CommandLineRunner runner (CustomerRepository customerRepository) {
        return args -> {

            Customer alex = new Customer(
                    "Alex",
                    "alex@gmail.com",
                    21,
                    "06-1234567"
            );

            Customer jamila = new Customer(
                    "Jamila",
                    "jamila@gmail.com",
                    22,
                    "06-1234589"
            );

            Customer bob = new Customer(
                    "Bob",
                    "bob@gmail.com",
                    25,
                    "06-98765432"
            );

            List<Customer> customers = List.of(alex, jamila, bob);
            customerRepository.saveAll(customers);

        };
    };
}
