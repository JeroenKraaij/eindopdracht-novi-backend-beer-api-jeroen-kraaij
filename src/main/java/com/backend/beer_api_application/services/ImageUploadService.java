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

@Service
public class ImageUploadService {

    @Autowired
    private ImageUploadRepository imageUploadRepository;

    @Autowired
    private BeerRepository beerRepository;

    @Transactional
    public ImageUpload uploadImage(Long beerId, MultipartFile file) throws IOException {
        Beer beer = beerRepository.findById(beerId).orElseThrow();

        String fileName = file.getOriginalFilename();
        String contentType = file.getContentType();
        long size = file.getSize();
        byte[] imageData = file.getBytes();

        ImageUpload image = new ImageUpload();
        image.setFileName(fileName);
        image.setImageData(imageData);
        image.setSize(size);
        image.setContentType(contentType);
        image.setUploadDate(LocalDateTime.now());
        image.setBeer(beer);
        image.setFileExtension(getFileExtension(fileName));
        image.setIsFeatured(beer.getImageUploads().isEmpty());

        return imageUploadRepository.save(image);
    }

    public boolean existsBeerById(Long beerId) {
        return beerRepository.existsById(beerId);
    }

    private String getFileExtension(String fileName) {
        if (fileName != null && fileName.contains(".")) {
            return fileName.substring(fileName.lastIndexOf(".") + 1);
        }
        return null;
    }
}
