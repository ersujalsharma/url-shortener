package com.project2026.url_shortener.controller;

import java.io.IOException;
import java.util.Map;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.project2026.url_shortener.service.UrlService;

import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class UrlController {

    private final UrlService service;

    @PostMapping("/shorten")
    public Map<String, String> shorten(@RequestBody Map<String, String> req) {
        String code = service.shorten(req.get("url"));
        return Map.of("shortUrl", "http://localhost:8080/" + code);
    }

    @GetMapping("/{code}")
    public void redirect(@PathVariable String code,
                         HttpServletResponse response) throws IOException {
        response.sendRedirect(service.resolve(code));
    }
}
