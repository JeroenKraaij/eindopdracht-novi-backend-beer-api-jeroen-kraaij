package com.backend.beer_api_application.repositories;

import com.backend.beer_api_application.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, String> {
}
