package com.episen.frontconverter.endpoints;

import com.episen.frontconverter.model.Client;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

@RequestMapping("/client")
@Slf4j
@RestController
@CrossOrigin("*")
public class ClientController {

    RestTemplate rs = new RestTemplate();

    @PostMapping("/suscribe")
    public Client suscribe(@RequestBody Client id){
        log.info("POST: client/suscribe: "+id);

        ResponseEntity<Client> response =
                rs.postForEntity(
                        "http://websockethub:8282/ws-admin/create",
                        id, Client.class);
        return response.getBody();
    }
}
