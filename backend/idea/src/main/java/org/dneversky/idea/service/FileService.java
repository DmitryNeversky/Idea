package org.dneversky.idea.service;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Collection;
import java.util.List;
import java.util.Map;

public interface FileService {
    Map<String, String> saveFiles(Collection<MultipartFile> multipartFiles) throws IOException;
    List<String> removeFiles(Collection<String> fileNames) throws IOException;
}
