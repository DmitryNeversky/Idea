package org.dneversky.idea.service;

import org.springframework.web.multipart.MultipartFile;

import java.util.Collection;
import java.util.List;

public interface ImageService {
    String saveImage(MultipartFile multipartFile);
    boolean removeImage(String fileName);
    List<String> saveImages(Collection<MultipartFile> multipartFiles);
    List<String> removeImages(Collection<String> fileNames);
}
