package com.episen.websockethub.endpoints;

import com.episen.websockethub.model.Client;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/ws-admin")
@Slf4j
@RestController
@CrossOrigin("*")
public class WebSocketConroller {

    @PostMapping("/create")
    public Client create(@RequestBody Client client){
        log.info("/ws-admin/create : "+client.toString());
        client.setWslocation("ws://websockethub:8383/image_status/"+client.getId());
        return client;
    }

}
