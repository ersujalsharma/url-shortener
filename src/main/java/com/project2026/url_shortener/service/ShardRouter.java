package com.project2026.url_shortener.service;

import java.util.Map;

import javax.sql.DataSource;

import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class ShardRouter {

    private final Map<Integer, DataSource> shardDataSources;
    private static final int SHARD_COUNT = 2;

    public DataSource route(String shortCode) {
        int shardId = Math.abs(shortCode.hashCode()) % SHARD_COUNT;
        System.out.println("Routing to shard ID: " + shardId + " for short code: " + shortCode);
        return shardDataSources.get(shardId);
    }
}