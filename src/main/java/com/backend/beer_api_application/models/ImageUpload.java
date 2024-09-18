package com.backend.beer_api_application.models;

import com.backend.beer_api_application.enums.ImageStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Table (name = "image_uploads")
public class ImageUpload {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String fileName;

    @Lob
    @Column(nullable = false)
    private byte[] imageData;

    @Column(nullable = false)
    private Long size;

    @Column(nullable = false)
    private String contentType;

    @Column(nullable = false)
    private LocalDateTime uploadDate = LocalDateTime.now();

    @Column(length = 255)
    private String altText;

    private Integer width;
    private Integer height;

    @Column(unique = true)
    private String hash;

    @Column(nullable = false)
    private Boolean isFeatured = false;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ImageStatus status = ImageStatus.ACTIVE;

    @Column(nullable = false)
    private String fileExtension;

    @ManyToOne
    @JoinColumn( nullable = false)
    private Beer beer;

    public ImageUpload() {}

    public ImageUpload(String fileName, byte[] imageData, Long size, String contentType, Beer beer, String fileExtension) {
        this.fileName = fileName;
        this.imageData = imageData;
        this.size = size;
        this.contentType = contentType;
        this.beer = beer;
        this.fileExtension = fileExtension;
    }
}