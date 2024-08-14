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

//    @Bean
//    CommandLineRunner runner(CustomerRepository customerRepository) {
//        return args -> {
//
//            Customer alex = new Customer(
//                    "Alex",
//                    "Holler",
//                    "Church place",
//                    "89F",
//                    "8905 TH",
//                    "Munchen",
//                    "alex@gmail.com",
//                    "06-1234567",
//                    "04 March 1974"
//
//
//            );
//
//            Customer jamila = new Customer(
//                    "Jamila",
//                    "Holler",
//                    "Church place",
//                    "89F",
//                    "8905 TH",
//                    "Munchen",
//                    "jamila@gmail.com",
//                    "06-1234589",
//                    "04 March 1974"
//            );
//
//            Customer bob = new Customer(
//                    "Bob",
//                    "MC Kancy",
//                    "5th Street",
//                    "2790",
//                    "NY 578",
//                    "New York",
//                    "bob@gmail.com",
//                    "06-98765432",
//                    "04 March 1974"
//            );
//
//            List<Customer> customers = List.of(alex, jamila, bob);
//            customerRepository.saveAll(customers);
//
//        };
//    }
}
