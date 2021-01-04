package com.vleite.cursomc.services;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.PutObjectRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;

@Service
public class S3Service {
    @Autowired
    private AmazonS3  s3Client;

    @Value("${s3.bucket}")
    private String bucketName;

    public void uploadFile(String localFilePath) {
        File file = new File(localFilePath);
        s3Client.putObject(new PutObjectRequest(bucketName, "teste", file));
    }
}
