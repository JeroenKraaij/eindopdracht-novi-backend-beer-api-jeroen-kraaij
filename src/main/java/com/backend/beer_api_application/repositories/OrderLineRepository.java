package com.backend.beer_api_application.repositories;

import com.backend.beer_api_application.models.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderLineRepository extends JpaRepository<Order, Long> {
}