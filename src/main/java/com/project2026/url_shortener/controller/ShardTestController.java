package com.project2026.url_shortener.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.project2026.url_shortener.repository.ShardedUrlRepository;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class ShardTestController {

    private final ShardedUrlRepository repo;

    @GetMapping("/test/{code}")
    public String test(@PathVariable String code) {
        return repo.findLongUrl(code);
    }
}
