package org.chiches.foodrootservir.services;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface StorageService {
    String uploadFile(MultipartFile file, String name);
    boolean fileExists(String bucketName, String objectKey);
}
