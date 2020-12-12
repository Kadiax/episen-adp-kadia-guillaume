package com.episen.frontconverter.endpoints;

import com.episen.frontconverter.model.Image;
import com.episen.frontconverter.model.Notification;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
public class NotificationController {

    @PostMapping("/notifications")
    public void convert(@RequestBody Notification notif){
        log.info("Notification: "+notif.getMsg());
    }
}
