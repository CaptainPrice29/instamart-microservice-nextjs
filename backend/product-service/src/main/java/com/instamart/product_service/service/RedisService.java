package com.instamart.product_service.service;

import java.util.concurrent.TimeUnit;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class RedisService {
    private final RedisTemplate<String, String> redisTemplate;

    public RedisService(RedisTemplate<String, String> redisTemplate) {
        this.redisTemplate = redisTemplate;

    }

    public void setValue(String key, Object value,Long ttlInSeconds) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            String jsonValue = objectMapper.writeValueAsString(value);
            redisTemplate.opsForValue().set(key, jsonValue, ttlInSeconds, TimeUnit.SECONDS);
        } catch (Exception e) {
            log.error("Error setting value in Redis for key {}: {}", key, e.getMessage());
        }
    }

    public <T> T getValue(String key, Class<T> entityType) {
        try {
            Object value = redisTemplate.opsForValue().get(key);
            ObjectMapper objectMapper = new ObjectMapper();
            return objectMapper.readValue(value.toString(), entityType);
        } catch (Exception e) {
            log.error("Error getting value from Redis for key {}: {}", key, e.getMessage());
            return null;
        }
    }
     public <T> T getValue(String key, TypeReference<T> entityType) {
        try {
            Object value = redisTemplate.opsForValue().get(key);
            ObjectMapper objectMapper = new ObjectMapper();
            return objectMapper.readValue(value.toString(), entityType);
        } catch (Exception e) {
            log.error("Error getting value from Redis for key {}: {}", key, e.getMessage());
            return null;
        }
    }

}
