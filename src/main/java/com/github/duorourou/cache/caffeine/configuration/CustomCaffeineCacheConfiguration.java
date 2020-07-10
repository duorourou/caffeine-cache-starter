package com.github.duorourou.cache.caffeine.configuration;

import com.github.duorourou.cache.caffeine.configuration.cluster.ClusterCaffeineSetting;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.caffeine.CaffeineCache;
import org.springframework.cache.support.SimpleCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
@EnableCaching
@ComponentScan("com.github.duorourou.cache.caffeine.configuration")
public class CustomCaffeineCacheConfiguration {

    private final CaffeineCacheSetting caffeineCacheSetting;
    private final ClusterCaffeineSetting clusterCaffeineSetting;

    public CustomCaffeineCacheConfiguration(CaffeineCacheSetting caffeineCacheSetting,
                                            ClusterCaffeineSetting clusterCaffeineSetting) {
        this.caffeineCacheSetting = caffeineCacheSetting;
        this.clusterCaffeineSetting = clusterCaffeineSetting;
    }

    @Bean
    public CacheManager cacheManager(CaffeineCache lockCache) {
        SimpleCacheManager cacheManager = new SimpleCacheManager();
        List<CaffeineCache> caches = caffeineCacheSetting.toCaffeineCaches(cacheManager);
        caches.add(lockCache);
        cacheManager.setCaches(caches);
        return cacheManager;
    }

    @Bean
    public CaffeineCache lockCache() {
        return clusterCaffeineSetting.toCache();
    }
}
