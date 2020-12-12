package com.episen.workerconverter.services;

import com.episen.workerconverter.model.Image;
import com.episen.workerconverter.model.ImageProperties;
import com.episen.workerconverter.model.Notification;
import com.episen.workerconverter.model.ReportingImage;
import com.episen.workerconverter.repository.ImagePropertiesRepository;
import com.episen.workerconverter.repository.ReportingImageRepository;
import com.google.gson.GsonBuilder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.http.WebSocket;
import java.nio.file.Path;
import java.nio.file.Paths;
import com.google.gson.Gson;
import org.springframework.web.client.RestTemplate;

@Slf4j
@Service
public class RabbitMQListner implements MessageListener {
    final GsonBuilder builder = new GsonBuilder();
    final Gson gson = builder.create();

    @Autowired
    private ImagePropertiesRepository imagePropertiesRepository;

    @Autowired
    private ReportingImageRepository reportingImageRepository;


    @Autowired
    private AmazonService amazonClient;

    private RestTemplate restTemplate = new RestTemplate();

    public void onMessage(Message message) {

        String msg = new String(message.getBody());
        Image img = new Gson().fromJson(msg, Image.class);
        System.out.println("Consuming Message - " + img);
        convertPNGToJPG(img.getId());


    }

    public boolean convertPNGToJPG(String id) {
        long t1 = System.currentTimeMillis();//conversion begin
        ImageProperties properties = imagePropertiesRepository.findById(id).get();

        if(this.amazonClient.isImageInBucket(id)) {
            try {
                BufferedImage originalImage = this.amazonClient.getImageFromBucketById(id);
                log.info("Image AWS pixel size :" + originalImage.getColorModel().getPixelSize());


                // jpg needs BufferedImage.TYPE_INT_RGB
                // png needs BufferedImage.TYPE_INT_ARGB

                // create a blank, RGB, same width and height
                BufferedImage newBufferedImage = new BufferedImage(
                        originalImage.getWidth(),
                        originalImage.getHeight(),
                        BufferedImage.TYPE_INT_RGB);

                if (properties.isCanalAlpha()) {
                    // draw a white background and puts the originalImage on it.
                    newBufferedImage.createGraphics()
                            .drawImage(originalImage,
                                    0,
                                    0,
                                    Color.WHITE,
                                    null);
                }


                // save an image
                Path target = Paths.get("/tmp/jpg/" + id + ".jpg");
                ImageIO.write(newBufferedImage, "jpg", target.toFile());
                File imgConverted = new File("/tmp/jpg/"+id+".jpg");

                this.amazonClient.putObject(imgConverted , id);
                log.info("Image : " + imgConverted.getName() + " converted");
                long t2 = System.currentTimeMillis();
                long t3 = t2 - t1;

                //save image properties
                ReportingImage reportingImage = new ReportingImage();
                reportingImage.setIdImageProperties(properties.getId());
                reportingImage.setCanalAlpha(newBufferedImage.getColorModel().hasAlpha());
                reportingImage.setWidth(newBufferedImage.getWidth());
                reportingImage.setHeight(newBufferedImage.getHeight());
                reportingImage.setPathFileConverted(id + ".jpg");
                reportingImage.setPixelSize(newBufferedImage.getColorModel().getPixelSize());
                reportingImage.setConvertedTime(arround(t3 * 0.001, 2));
                reportingImageRepository.save(reportingImage);
                log.info("Image converted properties saved " + reportingImage.toString());

                try{
                    //send notification
                    restTemplate.postForEntity(
                            "http://localhost:8080/notifications",
                            new Notification("Image "+imgConverted.getName()+" converted"),
                            Notification.class);


                    log.info("Notification send");
                }catch (Exception e){
                    log.info("Error sending notification to client "+e.getMessage());
                }
                return true;
            } catch (Exception e){
                log.info("id not found "+e.getMessage());
            }
        }

        log.info("File not found" );
        return false;
    }

    public double arround(double number,int nbAfterComma)
    {
        double power = Math.pow(10.0, nbAfterComma);
        return Math.round(number*power)/power;
    }
}
