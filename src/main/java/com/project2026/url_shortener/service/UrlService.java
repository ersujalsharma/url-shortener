package com.project2026.url_shortener.service;

import java.time.Duration;
import org.springframework.stereotype.Service;

import com.project2026.url_shortener.generator.Base62Generator;
import com.project2026.url_shortener.generator.RedisIdGenerator;
import com.project2026.url_shortener.model.UrlMapping;
import com.project2026.url_shortener.repository.ShardedUrlRepository;
import com.project2026.url_shortener.repository.UrlRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UrlService {

//    private final UrlRepository repository;
    private final RedisIdGenerator redisIdGenerator;
    private final Base62Generator generator;
    private final CacheService cacheService;
    private final ShardedUrlRepository repository;
    
    private static final Duration CACHE_TTL = Duration.ofHours(24);

    public String shorten(String longUrl) {
    	// Added Redis ID generator to ensure unique IDs across distributed instances	
    	long id = redisIdGenerator.getNextId();
        String shortCode = generator.generate(id);
        UrlMapping mapping = new UrlMapping();
        mapping.setShortCode(shortCode);
        mapping.setLongUrl(longUrl);
        repository.save(mapping);
		cacheService.put(shortCode, longUrl, CACHE_TTL);
        return shortCode;
    }

    public String resolve(String shortCode) {
    	String cachedUrl = cacheService.get(shortCode);
    	if(cachedUrl!=null) {
    		return cachedUrl;
    	}
//    	UrlMapping mapping = repository.findById(shortCode)
//                .orElseThrow(() -> new RuntimeException("URL not found"));
    	String longUrl = repository.findLongUrl(shortCode);
		if(longUrl==null) {
			throw new RuntimeException("URL not found");
		}
		UrlMapping mapping = new UrlMapping();
		mapping.setShortCode(shortCode);
		mapping.setLongUrl(longUrl);
		cacheService.put(shortCode, mapping.getLongUrl(), CACHE_TTL);
        return mapping.getLongUrl();
    }
}
