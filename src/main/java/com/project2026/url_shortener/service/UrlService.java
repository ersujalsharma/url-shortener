package com.project2026.url_shortener.service;

import java.time.Duration;
import org.springframework.stereotype.Service;

import com.project2026.url_shortener.generator.Base62Generator;
import com.project2026.url_shortener.generator.RedisIdGenerator;
import com.project2026.url_shortener.model.UrlMapping;
import com.project2026.url_shortener.repository.UrlRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UrlService {

    private final UrlRepository repository;
    private final RedisIdGenerator redisIdGenerator;
    private final Base62Generator generator;
    private final CacheService cacheService;
    
    private static final Duration CACHE_TTL = Duration.ofHours(24);

    public String shorten(String longUrl) {
    	// Added Redis ID generator to ensure unique IDs across distributed instances	
    	long id = redisIdGenerator.getNextId();
        String shortCode = generator.generate(id);
        UrlMapping mapping = new UrlMapping();
        mapping.setShortCode(shortCode);
        mapping.setLongUrl(longUrl);
        repository.save(mapping);
        try {
			cacheService.put(shortCode, longUrl, CACHE_TTL);
		}
		catch (Exception e) {
			System.err.println("Connection Disconnected");
		}
        return shortCode;
    }

    public String resolve(String shortCode) {
    	try {
	    	String cachedUrl = cacheService.get(shortCode);
	    	if(cachedUrl!=null) {
	    		return cachedUrl;
	    	}
    	}
    	catch (Exception e) {
			// TODO: handle exception
    		System.err.println("Connection Disconnected");
		}
    	UrlMapping mapping = repository.findById(shortCode)
                .orElseThrow(() -> new RuntimeException("URL not found"));
    	try {
    		cacheService.put(shortCode, mapping.getLongUrl(), CACHE_TTL);
    	}
    	catch (Exception e) {
			// TODO: handle exception
    		System.err.println("Connection Disconnected");
		}
        return mapping.getLongUrl();
    }
}
