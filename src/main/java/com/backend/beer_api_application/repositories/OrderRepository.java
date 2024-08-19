package com.backend.beer_api_application.repositories;

import com.backend.beer_api_application.models.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import com.backend.beer_api_application.models.Order;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {

}
