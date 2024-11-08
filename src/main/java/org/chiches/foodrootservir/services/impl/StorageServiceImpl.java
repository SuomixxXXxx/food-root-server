package org.chiches.foodrootservir.services.impl;

import org.chiches.foodrootservir.services.StorageService;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.HeadObjectRequest;
import software.amazon.awssdk.services.s3.model.NoSuchKeyException;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.services.s3.model.PutObjectResponse;

import java.io.IOException;
import java.net.URI;
import java.nio.file.Paths;

@Service
public class StorageServiceImpl implements StorageService {
    private final S3Client s3;

    public StorageServiceImpl() {
        this.s3 = S3Client.builder()
                .endpointOverride(URI.create("https://storage.yandexcloud.net"))
                .credentialsProvider(StaticCredentialsProvider.create(AwsBasicCredentials.create("YCAJERbo2_E2ZL2Y7E6kAZxzg", "YCNRWZT0GshdkvPWrvUicqwqel9pExfutvqgjV_1")))
                .region(Region.EU_NORTH_1)
                .build();
    }

    @Override
    public String uploadFile(MultipartFile file, String name) {
        PutObjectRequest request = PutObjectRequest.builder()
                .bucket("food-root")
                .key(name)
                .build();
        try {
            PutObjectResponse response = s3.putObject(request, RequestBody.fromInputStream(file.getInputStream(), file.getSize()));
            System.out.println("File uploaded: " + response);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return String.format("https://storage.yandexcloud.net/%s/%s", "food-root", name);
    }

    @Override
    public boolean fileExists(String bucketName, String objectKey) {
        boolean flag = true;
        try {
            s3.headObject(HeadObjectRequest.builder()
                    .bucket(bucketName)
                    .key(objectKey)
                    .build());
        } catch (NoSuchKeyException e) {
            flag = false; 
        }
        return flag;
    }
}

