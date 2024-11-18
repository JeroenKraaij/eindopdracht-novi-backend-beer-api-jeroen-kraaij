package com.backend.beer_api_application.repositories;

import com.backend.beer_api_application.models.ImageUpload;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ImageUploadRepository extends JpaRepository<ImageUpload, Long> {
}
