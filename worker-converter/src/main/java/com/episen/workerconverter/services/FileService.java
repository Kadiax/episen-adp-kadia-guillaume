package com.episen.workerconverter.services;

import com.episen.workerconverter.model.ImageProperties;
import com.episen.workerconverter.repository.ImagePropertiesRepository;
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

    public BufferedImage findImageById(String id){
        BufferedImage newBufferedImage = null;
        for(File f : files){
            if(f.getName().startsWith(id)) {
                try {
                    newBufferedImage = ImageIO.read(f);

                    return newBufferedImage;
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        return newBufferedImage;
    }


}
