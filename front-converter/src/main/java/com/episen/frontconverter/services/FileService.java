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

    public FileService(ImagePropertiesRepository imagePropertiesRepository) {
        this.imagePropertiesRepository = imagePropertiesRepository;
    }

    public boolean findById(String id){
        for(File f : files){
            if(f.getName().startsWith(id)) {
                try {
                    BufferedImage newBufferedImage = ImageIO.read(f);
                    ImageProperties details = new ImageProperties();
                    details.setId(id);
                    details.setCanalAlpha(newBufferedImage.getColorModel().hasAlpha());
                    details.setWidth(newBufferedImage.getWidth());
                    details.setHeight(newBufferedImage.getHeight());


                    imagePropertiesRepository.save(details);
                    log.info(imagePropertiesRepository.findAll().toString());
                    return true;
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        return false;
    }


}
