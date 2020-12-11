package com.episen.frontconverter.endpoints;

import com.episen.frontconverter.model.Image;
import com.episen.frontconverter.services.FileService;
import com.episen.frontconverter.services.RabbitMQSender;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

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
    public String convert(@RequestBody Image image){
        log.info(image.toString());
        if(fileservice.findById(image.getId())) {
            //rabbitMQSender.send(image);
            return "Message sent to the RabbitMQ Successfully";
        }
        return "ID not found. Please try again";

    }
}
