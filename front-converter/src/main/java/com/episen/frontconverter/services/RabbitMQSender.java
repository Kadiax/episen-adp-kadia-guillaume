package com.episen.frontconverter.services;

import com.episen.frontconverter.model.Image;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;


@Service
public class RabbitMQSender {

    @Autowired
    private AmqpTemplate rabbitTemplate;

    @Value("${episen.rabbitmq.exchange}")
    private String exchange;

    @Value("${episen.rabbitmq.routingkey}")
    private String routingkey;

    public void send(Image image) {
        rabbitTemplate.convertAndSend(exchange, routingkey, image);
        System.out.println("Send msg = " + image);

    }
}
