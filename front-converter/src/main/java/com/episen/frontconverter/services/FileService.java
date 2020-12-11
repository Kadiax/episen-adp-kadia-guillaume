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

    private static FilenameFilter pngFileFilter = (dir, name) -> name.endsWith(".png");
    private static File[] files;
    private static File repertoire;
    private ImagePropertiesRepository imagePropertiesRepository;
    static{
        repertoire = new File(".././imgs/png");
        files=repertoire.listFiles(pngFileFilter);

    }

    private AmazonService amazonClient;

    public FileService(ImagePropertiesRepository imagePropertiesRepository, AmazonService amazonClient) {
        this.imagePropertiesRepository = imagePropertiesRepository;
        this.amazonClient = amazonClient;
    }

    public boolean findById(String id){
    /*
        for(File f : files){
            if(f.getName().startsWith(id)) {
                try {
                    BufferedImage newBufferedImage = ImageIO.read(f);
                    ImageProperties details = new ImageProperties();
                    details.setId(id);
                    details.setCanalAlpha(newBufferedImage.getColorModel().hasAlpha());
                    details.setPixelSize(newBufferedImage.getColorModel().getPixelSize());
                    details.setWidth(newBufferedImage.getWidth());
                    details.setHeight(newBufferedImage.getHeight());
                    details.setPathFileOriginal(".././"+f.getName());

                    imagePropertiesRepository.save(details);
                    log.info("Image send to rabbitMQ:  "+details.toString());
                    return true;
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }*/
        if(this.amazonClient.isImageInBucket(id)){
            BufferedImage newBufferedImage = this.amazonClient.getImageFromBucketById(id);
            log.info("Image AWS pixel size :"+ newBufferedImage.getColorModel().getPixelSize());
            return true;
        }
        return false;
    }


}
