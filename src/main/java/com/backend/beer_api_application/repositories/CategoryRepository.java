package com.backend.beer_api_application.repositories;

import com.backend.beer_api_application.models.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
    Optional<Category> findByBeerCategoryName(String beerCategoryName);
}
