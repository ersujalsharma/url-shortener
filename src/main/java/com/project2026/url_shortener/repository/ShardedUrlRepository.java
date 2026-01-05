package com.project2026.url_shortener.repository;

import javax.sql.DataSource;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.project2026.url_shortener.model.UrlMapping;
import com.project2026.url_shortener.service.ShardRouter;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class ShardedUrlRepository {

    private final ShardRouter shardRouter;

    public void save(UrlMapping mapping) {
        DataSource ds = (DataSource) shardRouter.route(mapping.getShortCode());
        JdbcTemplate jdbc = new JdbcTemplate(ds);

        jdbc.update(
            "INSERT INTO url_mapping (short_code, long_url) VALUES (?, ?)",
            mapping.getShortCode(),
            mapping.getLongUrl()
        );
    }

    public String findLongUrl(String shortCode) {
        DataSource ds = (DataSource) shardRouter.route(shortCode);
        JdbcTemplate jdbc = new JdbcTemplate(ds);

        try {
            return jdbc.queryForObject(
                "SELECT long_url FROM url_mapping WHERE short_code = ?",
                String.class,
                shortCode
            );
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }
}
