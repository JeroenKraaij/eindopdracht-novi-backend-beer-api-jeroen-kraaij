package com.backend.beer_api_application.repositories;

import com.backend.beer_api_application.models.Beer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BeerRepository
extends JpaRepository<Beer, Long> {
    List<Beer> findAllBeersByBrandEqualsIgnoreCase(String brand);
    Optional<Beer> findByName(String name);


}
