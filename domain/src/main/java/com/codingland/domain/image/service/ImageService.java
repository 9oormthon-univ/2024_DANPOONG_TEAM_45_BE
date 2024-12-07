package com.codingland.domain.image.service;

import com.codingland.domain.image.entity.Image;
import com.codingland.domain.image.repository.ImageRepository;
import com.codingland.domain.image.utils.ImageUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ImageService {
    private final ImageRepository imageRepository;

    @Transactional(readOnly = true)
    public byte[] downloadImage(String fileName) {
        Image foundImage = imageRepository.findByName(fileName)
                .orElseThrow(() -> new RuntimeException("test"));
        return ImageUtils.decompressImage(foundImage.getImageData());
    }
}
