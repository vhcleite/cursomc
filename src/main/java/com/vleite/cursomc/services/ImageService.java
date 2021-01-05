package com.vleite.cursomc.services;

import org.apache.commons.io.FilenameUtils;
import org.imgscalr.Scalr;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.Buffer;

@Service
public class ImageService {

    public BufferedImage getImageFromFile(MultipartFile file) {
        String ext = FilenameUtils.getExtension(file.getOriginalFilename());
        if(!"png".equals(ext) && !"jpg".equals(ext)) {
            throw new RuntimeException("Somente imagens PNG e JPG são permitidas");
        }

        try {
            BufferedImage img = ImageIO.read(file.getInputStream());
            if("png".equals(ext)) {
                img = pngToJpg(img);
            }
            return img;
        } catch (IOException e) {
            throw new RuntimeException("Erro ao ler arquivo");
        }

    }

    private BufferedImage pngToJpg(BufferedImage img) {
        BufferedImage bufferedImage = new BufferedImage(img.getWidth(), img.getHeight(), BufferedImage.TYPE_INT_RGB);
        bufferedImage.createGraphics().drawImage(img, 0, 0, Color.WHITE, null);
        return bufferedImage;
    }

    public InputStream getInputStream(BufferedImage img, String extension) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        try {
            ImageIO.write(img, extension, byteArrayOutputStream);
        } catch (IOException e) {
            throw new RuntimeException("Erro ao ler arquivo");
        }
        return new ByteArrayInputStream(byteArrayOutputStream.toByteArray());
    }

    public BufferedImage resize(BufferedImage sourceImg, int size) {
        return Scalr.resize(sourceImg, Scalr.Method.ULTRA_QUALITY, size);
    }
}
