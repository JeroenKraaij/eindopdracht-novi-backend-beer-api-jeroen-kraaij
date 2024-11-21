package com.backend.beer_api_application.repositories;

import com.backend.beer_api_application.models.ImageUpload;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ImageUploadRepository extends JpaRepository<ImageUpload, Long> {

    List<ImageUpload> findByBeerId(Long beerId);

    Optional<ImageUpload> findByBeerIdAndIsFeaturedTrue(Long beerId);

    void deleteByBeerId(Long beerId);
}
