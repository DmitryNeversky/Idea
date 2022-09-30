package org.dneversky.idea.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.dneversky.idea.service.ImageService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.*;

@Slf4j
@Service
public class ImageServiceImpl implements ImageService {

    @Value("${uploadPath}")
    private String UPLOAD_PATH;

    @Override
    public String saveImage(MultipartFile multipartFile) {
        String fileName = java.util.UUID.randomUUID() + "_"
                + StringUtils.cleanPath(Objects.requireNonNull(multipartFile.getOriginalFilename()));
        try {
            Path path = Paths.get(UPLOAD_PATH + "images/avatar/" + fileName);
            Files.copy(multipartFile.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            log.error("Uploading image error: {}", e.getMessage());
        }
        return fileName;
    }

    @Override
    public boolean removeImage(String fileName) {
        if (fileName != null) {
            if (Files.exists(Paths.get(UPLOAD_PATH + "images/avatar/" + fileName))) {
                try {
                    Files.delete(Paths.get(UPLOAD_PATH + "images/avatar/" + fileName));
                } catch (IOException e) {
                    log.error("Removing image error: {}", e.getMessage());
                    return false;
                }
            }
        }
        return true;
    }

    @Override
    public List<String> saveImages(Collection<MultipartFile> multipartFiles) {
        List<String> savedImages = new ArrayList<>();
        if(multipartFiles != null) {
            for (MultipartFile pair : multipartFiles) {
                if (Objects.requireNonNull(pair.getOriginalFilename()).isEmpty())
                    continue;
                String fileName = java.util.UUID.randomUUID() + "_"
                        + StringUtils.cleanPath(Objects.requireNonNull(pair.getOriginalFilename()));
                try {
                    Path path = Paths.get(UPLOAD_PATH + "images/" + fileName);
                    Files.copy(pair.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);
                    savedImages.add(fileName);
                } catch (IOException e) {
                    log.error("Uploading images error: {}", e.getMessage());
                }
            }
        }
        return savedImages;
    }

    @Override
    public List<String> removeImages(Collection<String> fileNames) {
        List<String> removedImages = new ArrayList<>();
        if (fileNames != null) {
            for (String pair : fileNames) {
                if (Files.exists(Paths.get(UPLOAD_PATH + "images/" + pair))) {
                    try {
                        Files.delete(Paths.get(UPLOAD_PATH + "images/" + pair));
                        removedImages.add(pair);
                    } catch (IOException e) {
                        log.error("Removing images error: {}", e.getMessage());
                    }
                }
            }
        }
        return removedImages;
    }
}
