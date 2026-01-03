package com.project2026.url_shortener.generator;

import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class RedisIdGenerator {

    private final StringRedisTemplate stringRedisTemplate;

    private static final String KEY = "url:id:counter";

    public long getNextId() {
        return stringRedisTemplate.opsForValue().increment(KEY);
    }
}
