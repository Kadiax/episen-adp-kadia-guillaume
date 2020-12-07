package com.episen.workerconverter.services;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageListener;
import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
public class RabbitMQListner implements MessageListener {
    private Path source = Paths.get("./imgs/png/java-duke.png");
    private Path target = Paths.get("./imgs/jpg/java-duke.jpg");


    public void onMessage(Message message) {
        System.out.println("Consuming Message - " + new String(message.getBody()));
        convertPNGToJPG();
    }

    public boolean convertPNGToJPG() {


        BufferedImage originalImage = null;
        try {
            originalImage = ImageIO.read(source.toFile());

            // jpg needs BufferedImage.TYPE_INT_RGB
            // png needs BufferedImage.TYPE_INT_ARGB

            // create a blank, RGB, same width and height
            BufferedImage newBufferedImage = new BufferedImage(
                    originalImage.getWidth(),
                    originalImage.getHeight(),
                    BufferedImage.TYPE_INT_RGB);

            // draw a white background and puts the originalImage on it.
            newBufferedImage.createGraphics()
                    .drawImage(originalImage,
                            0,
                            0,
                            Color.WHITE,
                            null);

            // save an image
            ImageIO.write(newBufferedImage, "jpg", target.toFile());
            return true;
        } catch (IOException e) {
            e.printStackTrace();
        }

        return false;
    }
}
