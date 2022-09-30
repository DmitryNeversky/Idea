package org.dneversky.idea.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.dneversky.idea.service.FileService;
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
public class FileServiceImpl implements FileService {

    @Value("${uploadPath}")
    private String UPLOAD_PATH;

    @Override
    public Map<String, String> saveFiles(Collection<MultipartFile> multipartFiles) {
        Map<String, String> savedFiles = new HashMap<>();
        if(multipartFiles != null && multipartFiles.size() > 0) {
            for (MultipartFile pair : multipartFiles) {
                if (Objects.requireNonNull(pair.getOriginalFilename()).isEmpty())
                    continue;
                String fileName = java.util.UUID.randomUUID() + "_"
                        + StringUtils.cleanPath(Objects.requireNonNull(pair.getOriginalFilename()));
                try {
                    Path path = Paths.get(UPLOAD_PATH + "files/" + fileName);
                    Files.copy(pair.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);
                    savedFiles.put(fileName, pair.getOriginalFilename());
                } catch (IOException e) {
                    log.error("Uploading idea's files error: {}", e.getMessage());
                }
            }
        }
        return savedFiles;
    }

    @Override
    public List<String> removeFiles(Collection<String> fileNames) {
        List<String> removedFiles = new ArrayList<>();
        if(fileNames != null && fileNames.size() > 0) {
            for (String pair : fileNames) {
                if (Files.exists(Paths.get(UPLOAD_PATH + "files/" + pair))) {
                    try {
                        Files.delete(Paths.get(UPLOAD_PATH + "files/" + pair));
                        removedFiles.add(pair);
                    } catch (IOException e) {
                        log.error("Removing idea's files error: {}", e.getMessage());
                    }
                }
            }
        }
        return removedFiles;
    }
}
