package com.vleite.cursomc.services;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;

@Service
public class S3Service {
    @Autowired
    private AmazonS3 s3Client;

    @Value("${s3.bucket}")
    private String bucketName;

    public URI uploadFile(MultipartFile file) {
        try {
            InputStream is = file.getInputStream();
            String filename = file.getOriginalFilename();
            String contentType = file.getContentType();

            return uploadFile(is, filename, "jpg");
        } catch (IOException e) {
            throw new RuntimeException("Erro ao fazer upload de arquivo");
        }
    }

    public URI uploadFile(InputStream is, String filename, String extension) {
        try {
            ObjectMetadata objectMetadata = new ObjectMetadata();
            objectMetadata.setContentType("image");

            s3Client.putObject(bucketName, filename, is, objectMetadata);
            return s3Client.getUrl(bucketName, filename).toURI();
        } catch (URISyntaxException e) {
            throw new RuntimeException("Erro ao fazer upload de arquivo");
        }
    }
}
