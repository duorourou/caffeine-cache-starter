package com.github.duorourou.cache.caffeine.configuration;

import com.github.duorourou.cache.caffeine.annotation.EnableCaffeineCache;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cache.CacheManager;
import org.springframework.test.context.ContextConfiguration;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.notNullValue;

@SpringBootTest(classes = CustomCaffeineCacheConfiguration.class)
@ContextConfiguration
@EnableCaffeineCache
class CustomCaffeineCacheConfigurationTest {

    @Autowired
    CacheManager cacheManager;

    @Test
    public void could_init_cache_configuration_with_enablecaffeinecache_annotation() {
        assertThat(cacheManager, notNullValue());
    }
}