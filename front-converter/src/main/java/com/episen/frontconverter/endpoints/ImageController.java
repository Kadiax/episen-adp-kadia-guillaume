package com.episen.frontconverter.endpoints;

import com.episen.frontconverter.model.Image;
import com.episen.frontconverter.model.Notification;
import com.episen.frontconverter.services.FileService;
import com.episen.frontconverter.services.RabbitMQSender;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/image")
@Slf4j
@RestController
@CrossOrigin("*")
public class ImageController {
    @Autowired
    RabbitMQSender rabbitMQSender;

    @Autowired
    FileService fileservice;



    @PostMapping("/convert")
    public Notification convert(@RequestBody Image image){
        log.info("image/convert : "+image.toString());
        if(fileservice.findById(image.getId())) {
            rabbitMQSender.send(image);
            return new Notification("Message sent to the RabbitMQ Successfully");
        }
        return new Notification("ID not found. Please try again");

    }


}
