package com.project2026.url_shortener.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.project2026.url_shortener.model.UrlMapping;

@Repository
public interface UrlRepository extends JpaRepository<UrlMapping, String> {
	
}
