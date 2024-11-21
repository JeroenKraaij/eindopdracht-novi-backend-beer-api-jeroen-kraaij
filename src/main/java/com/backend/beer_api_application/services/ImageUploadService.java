package com.backend.beer_api_application.services;

import com.backend.beer_api_application.models.Beer;
import com.backend.beer_api_application.models.ImageUpload;
import com.backend.beer_api_application.repositories.BeerRepository;
import com.backend.beer_api_application.repositories.ImageUploadRepository;
import com.backend.beer_api_application.utils.ImageUploadHelper;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
public class ImageUploadService {

    @Autowired
    private ImageUploadRepository imageUploadRepository;

    @Autowired
    private BeerRepository beerRepository;

    @Transactional
    public ImageUpload uploadImage(Long beerId, MultipartFile file) throws IOException {
        Beer beer = beerRepository.findById(beerId).orElseThrow();
        ImageUpload image = ImageUploadHelper.createImageUpload(beer, file);
        return imageUploadRepository.save(image);
    }

    @Transactional
    public ImageUpload updateImage(Long imageId, MultipartFile file) throws IOException {
        ImageUpload existingImage = imageUploadRepository.findById(imageId).orElseThrow();

        existingImage.setFileName(file.getOriginalFilename());
        existingImage.setContentType(file.getContentType());
        existingImage.setSize(file.getSize());
        existingImage.setImageData(file.getBytes());
        existingImage.setFileExtension(ImageUploadHelper.getFileExtension(file.getOriginalFilename()));

        return imageUploadRepository.save(existingImage);
    }

    @Transactional
    public void deleteImage(Long imageId) {
        imageUploadRepository.deleteById(imageId);
    }

    public ImageUpload getImageById(Long imageId) {
        return imageUploadRepository.findById(imageId).orElseThrow();
    }
}
