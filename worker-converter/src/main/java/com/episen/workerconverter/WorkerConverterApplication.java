package com.episen.workerconverter;

import com.episen.workerconverter.services.RabbitMQListner;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class WorkerConverterApplication{
    private RabbitMQListner listner;

    public static void main(String[] args) {
        SpringApplication.run(WorkerConverterApplication.class, args);
    }


}
