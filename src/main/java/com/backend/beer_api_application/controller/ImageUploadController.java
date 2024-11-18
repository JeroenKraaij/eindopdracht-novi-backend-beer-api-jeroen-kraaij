package com.backend.beer_api_application.controller;

import com.backend.beer_api_application.exceptions.RecordNotFoundException;
import com.backend.beer_api_application.models.ImageUpload;
import com.backend.beer_api_application.services.ImageUploadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping(value = "api/v1")
public class ImageUploadController {

    @Autowired
    private ImageUploadService imageUploadService;

    @PostMapping("/images/upload/{beerId}")
    public ResponseEntity<ImageUpload> uploadImage(@PathVariable Long beerId, @RequestParam("file") MultipartFile file) {
        try {
            if (!imageUploadService.existsBeerById(beerId)) {
                throw new RecordNotFoundException("Beer with ID " + beerId + " not found");
            }

            ImageUpload image = imageUploadService.uploadImage(beerId, file);
            return ResponseEntity.ok(image);

        } catch (IOException e) {
            return ResponseEntity.status(500).body(null);
        } catch (RecordNotFoundException e) {
            return ResponseEntity.status(404).body(null);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(400).body(null);
        }
    }
}
