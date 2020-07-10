package com.github.duorourou.cache.caffeine.configuration;

import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.support.SimpleCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableCaching
@ComponentScan("com.github.duorourou.cache.caffeine.configuration")
public class CustomCaffeineCacheConfiguration {

    private final CaffeineCacheSetting caffeineCacheSetting;

    public CustomCaffeineCacheConfiguration(CaffeineCacheSetting caffeineCacheSetting) {
        this.caffeineCacheSetting = caffeineCacheSetting;
    }

    @Bean
    public CacheManager cacheManager() {
        SimpleCacheManager cacheManager = new SimpleCacheManager();
        cacheManager.setCaches(caffeineCacheSetting.toCaffeineCaches());
        return cacheManager;
    }
}
