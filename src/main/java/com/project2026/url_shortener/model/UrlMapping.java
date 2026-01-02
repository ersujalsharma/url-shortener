package com.project2026.url_shortener.model;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "url_mapping", indexes = {
    @Index(name = "idx_shortcode", columnList = "shortCode", unique = true)
})
@Getter @Setter
public class UrlMapping {

    @Id
    private String shortCode;

    @Column(nullable = false, length = 2048)
    private String longUrl;

    private LocalDateTime createdAt = LocalDateTime.now();
}
