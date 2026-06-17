package com.instamart.product_service.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;

@SpringBootTest
public class RedisTests {

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @Test
    void testRedisConnection() {
        // Set a value in Redis
        redisTemplate.opsForValue().set("testKey1", "testValue1");

        // Retrieve the value from Redis
        String value = redisTemplate.opsForValue().get("nawazish");

        // Assert that the value is correct
        assert "lola".equals(value);
    }

}
