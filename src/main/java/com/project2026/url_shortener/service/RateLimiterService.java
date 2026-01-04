package com.project2026.url_shortener.service;

import java.time.Duration;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class RateLimiterService {
	private final StringRedisTemplate redisTemplate;
	private static final int Limit = 10;
	private static final Duration WINDOW = Duration.ofMinutes(1);
	public boolean isAllowed(String key) {
		Long count = redisTemplate.opsForValue().increment(key);
		if(count!=null && count == 1) {
			// Set expiration only when the key is first created
			redisTemplate.expire(key, WINDOW);
		}
		return count != null && count <= Limit;
	}
}
