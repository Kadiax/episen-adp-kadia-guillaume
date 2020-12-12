package com.episen.frontconverter.services;

import com.episen.frontconverter.model.ImageProperties;
import com.episen.frontconverter.repository.ImagePropertiesRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;

@Slf4j
@Service
public class FileService {

    private ImagePropertiesRepository imagePropertiesRepository;
    private AmazonService amazonClient;

    public FileService(ImagePropertiesRepository imagePropertiesRepository, AmazonService amazonClient) {
        this.imagePropertiesRepository = imagePropertiesRepository;
        this.amazonClient = amazonClient;
    }

    public boolean findById(String id){

        if(this.amazonClient.isImageInBucket(id)){
            try {
                BufferedImage newBufferedImage = this.amazonClient.getImageFromBucketById(id);
                log.info("Image AWS pixel size :" + newBufferedImage.getColorModel().getPixelSize());
                ImageProperties details = new ImageProperties();
                details.setId(id);
                details.setCanalAlpha(newBufferedImage.getColorModel().hasAlpha());
                details.setPixelSize(newBufferedImage.getColorModel().getPixelSize());
                details.setWidth(newBufferedImage.getWidth());
                details.setHeight(newBufferedImage.getHeight());
                details.setPathFileOriginal(id + ".png");

                imagePropertiesRepository.save(details);
                log.info("Image send to rabbitMQ:  " + details.toString());
            }catch (Exception e){
                log.info("id not found");
            }
            return true;
        }
        return false;
    }


}
