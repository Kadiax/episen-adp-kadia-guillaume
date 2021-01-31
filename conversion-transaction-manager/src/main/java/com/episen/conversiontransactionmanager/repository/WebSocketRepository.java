package com.episen.conversiontransactionmanager.repository;

import com.episen.conversiontransactionmanager.model.WebSocket;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import java.util.Map;

@Repository
public class WebSocketRepository {
    private RedisTemplate<String, WebSocket> redisTemplate;

    private HashOperations hashOperations;

    public WebSocketRepository(RedisTemplate<String, WebSocket> redisTemplate) {
        this.redisTemplate = redisTemplate;
        hashOperations = redisTemplate.opsForHash();
    }


    public Map<Integer, WebSocket> findAll() {
        return hashOperations.entries("WebSocket");
    }

    public WebSocket findById(int id) {
        return (WebSocket) hashOperations.get("WebSocket", id);
    }

    public WebSocket save(WebSocket ws) {
        hashOperations.put("Product", ws.getId(), ws);
        return ws;
    }
}
