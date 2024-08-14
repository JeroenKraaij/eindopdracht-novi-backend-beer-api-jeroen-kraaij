package com.backend.beer_api_application.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import com.backend.beer_api_application.models.Order;

public interface OrderRepository extends JpaRepository<Order, Long> {
}
