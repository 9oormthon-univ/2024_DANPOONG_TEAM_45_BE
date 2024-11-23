package com.codingland.domain.image.service;

import com.codingland.domain.image.entity.Image;
import com.codingland.domain.image.repository.ImageRepository;
import com.codingland.domain.image.utils.ImageUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ImageService {
    private final ImageRepository imageRepository;

    public byte[] downloadImage(String fileName) {
        Image foundImage = imageRepository.findByName(fileName)
                .orElseThrow(() -> new RuntimeException("test"));
        return ImageUtils.decompressImage(foundImage.getImageData());
    }
}
