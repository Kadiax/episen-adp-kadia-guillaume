package com.episen.conversiontransactionmanager;

import com.episen.conversiontransactionmanager.model.WebSocket;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;

@SpringBootApplication
public class ConversionTransactionManagerApplication {

    @Bean
    @Autowired
    RedisTemplate<String, WebSocket> redisTemplate(RedisConnectionFactory redisConnectionFactory) {

        if (null == redisConnectionFactory) {
            System.out.println("Redis Template Service is not available");
            return null;
        }
        RedisTemplate<String, WebSocket> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(redisConnectionFactory);
        return redisTemplate;
    }
    public static void main(String[] args) {
        SpringApplication.run(ConversionTransactionManagerApplication.class, args);
    }

}
