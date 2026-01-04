package com.project2026.url_shortener.service;

import java.time.Duration;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
@Service
@RequiredArgsConstructor
public class RedisCacheService implements CacheService{

	private final RedisTemplate<String, String> redisTemplate;
	
	@Override
	public String get(String key) {
		// TODO Auto-generated method stub
		try {
			return redisTemplate.opsForValue().get(key);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			System.err.println("Connection to Redis failed. Caching skipped.");
			return null;
		}
	}

	@Override
	public void put(String key, String value, Duration ttl) {
		// TODO Auto-generated method stub
		try {
			redisTemplate.opsForValue().set(key, value, ttl);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.err.println("Connection to Redis failed. Caching skipped.");
		}
	}

}
