package com.backend.beer_api_application.repositories;

import com.backend.beer_api_application.models.Taste;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TasteRepository extends JpaRepository <Taste, Long> {
}
