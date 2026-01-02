package com.project2026.url_shortener.service;

import org.springframework.stereotype.Service;

import com.project2026.url_shortener.generator.Base62Generator;
import com.project2026.url_shortener.model.UrlMapping;
import com.project2026.url_shortener.repository.UrlRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UrlService {

    private final UrlRepository repository;
    private final Base62Generator generator;

    public String shorten(String longUrl) {
        String shortCode = generator.generate();
        UrlMapping mapping = new UrlMapping();
        mapping.setShortCode(shortCode);
        mapping.setLongUrl(longUrl);
        repository.save(mapping);
        return shortCode;
    }

    public String resolve(String shortCode) {
        return repository.findById(shortCode)
                .orElseThrow(() -> new RuntimeException("URL not found"))
                .getLongUrl();
    }
}
