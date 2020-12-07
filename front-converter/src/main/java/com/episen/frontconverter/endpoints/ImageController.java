package com.episen.frontconverter.endpoints;

import com.episen.frontconverter.model.Image;
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

    @PostMapping("/convert")
    public ResponseEntity<Image> convert(@RequestBody Image image){
        log.info(image.toString());
        rabbitMQSender.send(image);
        log.info("Message sent to the RabbitMQ JavaInUse Successfully");
        return ResponseEntity.created(URI.create(String.valueOf(image.getId()))).body(image);
    }
}
