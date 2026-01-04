package com.project2026.url_shortener.generator;

import java.util.concurrent.atomic.AtomicLong;

import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class RedisIdGenerator {

    private final StringRedisTemplate redisTemplate;
    private final AtomicLong fallbackCounter = new AtomicLong(System.currentTimeMillis());

    private static final String KEY = "url:id:counter";

    public long getNextId() {
    	try {
            return redisTemplate.opsForValue().increment(KEY);
        } catch (Exception e) {
            // Fallback (rare case)
            return fallbackCounter.incrementAndGet();
        }
    }
}
