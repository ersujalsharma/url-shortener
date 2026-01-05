package com.project2026.url_shortener.config;

import java.util.HashMap;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class ShardDataSourceConfig {
	@Bean
    public Map<Integer, DataSource> shardDataSources() {

        Map<Integer, DataSource> shards = new HashMap<>();

        shards.put(0, (DataSource) DataSourceBuilder.create()
                .url("jdbc:mysql://localhost:3306/url_shortener")
                .username("root")
                .password("sujal")
                .build());

        shards.put(1, (DataSource) DataSourceBuilder.create()
                .url("jdbc:mysql://localhost:3307/url_shortener")
                .username("root")
                .password("sujal")
                .build());

        return shards;
    }
}
