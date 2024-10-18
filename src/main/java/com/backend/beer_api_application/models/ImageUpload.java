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

    @Column
    private String fileName;

    @Lob
    @Column
    private byte[] imageData;

    @Column
    private Long size;

    @Column
    private String contentType;

    @Column
    private LocalDateTime uploadDate = LocalDateTime.now();

    @Column(length = 255)
    private String altText;

    private Integer width;
    private Integer height;

    @Column(unique = true)
    private String hash;

    @Column
    private Boolean isFeatured = false;

    @Enumerated(EnumType.STRING)
    @Column
    private ImageStatus status = ImageStatus.ACTIVE;

    @Column
    private String fileExtension;

    @ManyToOne
    @JoinColumn
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