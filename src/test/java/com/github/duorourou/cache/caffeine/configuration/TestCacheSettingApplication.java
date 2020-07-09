package com.github.duorourou.cache.caffeine.configuration;

import com.github.duorourou.cache.caffeine.configuration.cluster.CacheRemovalListener;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class TestCacheSettingApplication {

    public static void main(String[] args) {
        SpringApplication.run(TestCacheSettingApplication.class, args);
    }

    @Bean
    public CacheRemovalListener cacheRemovalListener() {
        return (k, v, c) -> {
        };
    }
}
