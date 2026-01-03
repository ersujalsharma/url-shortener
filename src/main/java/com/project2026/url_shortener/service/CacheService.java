package com.project2026.url_shortener.service;

import java.time.Duration;

public interface CacheService {
	String get(String key);
    void put(String key, String value, Duration ttl);
}
