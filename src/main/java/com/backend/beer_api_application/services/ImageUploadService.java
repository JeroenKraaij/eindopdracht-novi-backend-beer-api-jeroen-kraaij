package com.backend.beer_api_application.services;

import com.backend.beer_api_application.models.Beer;
import com.backend.beer_api_application.models.ImageUpload;
import com.backend.beer_api_application.repositories.BeerRepository;
import com.backend.beer_api_application.repositories.ImageUploadRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class ImageUploadService {

    @Autowired
    private ImageUploadRepository imageUploadRepository;

    @Autowired
    private BeerRepository beerRepository;

    @Transactional
    public ImageUpload uploadImage(Long beerId, MultipartFile file) throws IOException {
        // Validate Beer existence
        Optional<Beer> beerOptional = beerRepository.findById(beerId);
        if (beerOptional.isEmpty()) {
            throw new IllegalArgumentException("Beer with ID " + beerId + " does not exist");
        }
        Beer beer = beerOptional.get();

        // Get file metadata
        String fileName = file.getOriginalFilename();
        String contentType = file.getContentType();
        long size = file.getSize();
        byte[] imageData = file.getBytes();  // Get the binary data of the file

        // Create Image entity and populate it with data
        ImageUpload image = new ImageUpload();
        image.setFileName(fileName);
        image.setImageData(imageData);  // Store the binary data in the entity
        image.setSize(size);
        image.setContentType(contentType);
        image.setUploadDate(LocalDateTime.now());
        image.setBeer(beer);
        image.setFileExtension(getFileExtension(fileName));  // Helper function to extract file extension

        // Set the image as featured if it's the first image for this beer
        image.setIsFeatured(beer.getImageUploads().isEmpty());

        // Save the image to the database
        return imageUploadRepository.save(image);
    }

    // Helper function to extract file extension
    private String getFileExtension(String fileName) {
        if (fileName != null && fileName.contains(".")) {
            return fileName.substring(fileName.lastIndexOf(".") + 1);
        }
        return null;
    }
}
