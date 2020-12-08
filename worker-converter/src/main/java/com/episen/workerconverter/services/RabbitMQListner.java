package com.episen.workerconverter.services;

import com.episen.workerconverter.model.Image;
import com.episen.workerconverter.model.ImageProperties;
import com.episen.workerconverter.repository.ImagePropertiesRepository;
import com.google.gson.GsonBuilder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import com.google.gson.Gson;

@Slf4j
@Service
public class RabbitMQListner implements MessageListener {
    final GsonBuilder builder = new GsonBuilder();
    final Gson gson = builder.create();

    @Autowired
    private ImagePropertiesRepository repo ;

    @Autowired
    private FileService fileService;

    public void onMessage(Message message) {

        String msg = new String(message.getBody());
        Image img = new Gson().fromJson(msg, Image.class);
        System.out.println("Consuming Message - " + img);
        convertPNGToJPG(img.getId());
    }

    public boolean convertPNGToJPG(String id) {
        ImageProperties properties = repo.findById(id).get();
        BufferedImage originalImage = fileService.findImageById(id);
        Path target = Paths.get(".././imgs/jpg/"+id+".jpg");
        try {

            // jpg needs BufferedImage.TYPE_INT_RGB
            // png needs BufferedImage.TYPE_INT_ARGB

            // create a blank, RGB, same width and height
            BufferedImage newBufferedImage = new BufferedImage(
                    originalImage.getWidth(),
                    originalImage.getHeight(),
                    BufferedImage.TYPE_INT_RGB);

            if(properties.isCanalAlpha()){
                // draw a white background and puts the originalImage on it.
                newBufferedImage.createGraphics()
                        .drawImage(originalImage,
                                0,
                                0,
                                Color.WHITE,
                                null);
            }


            // save an image
            ImageIO.write(newBufferedImage, "jpg", target.toFile());
            log.info("Image : "+id+" converted");
            return true;
        } catch (IOException e) {
            e.printStackTrace();
        }

        return false;
    }
}
