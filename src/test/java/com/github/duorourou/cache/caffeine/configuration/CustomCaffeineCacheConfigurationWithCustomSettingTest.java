package com.github.duorourou.cache.caffeine.configuration;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cache.CacheManager;

import java.util.Objects;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest(classes = {TestCacheSettingApplication.class})
class CustomCaffeineCacheConfigurationWithCustomSettingTest {

    @Autowired
    CacheManager cacheManager;

    @Test
    public void could_init_cache_with_custom_settings() {
        //could init the cachemanager
        assertThat(cacheManager, notNullValue());
        //could init all custom caches
        assertThat(cacheManager.getCacheNames(), containsInAnyOrder("X", "Y", "CUSTOM-CAFFEINE-CACHE-CLUSTER-SYNC-CACHE"));

        //could
        Objects.requireNonNull(cacheManager.getCache("X")).put("X", "!=Y");
        assertTrue(Objects.requireNonNull(cacheManager.getCache("X")).evictIfPresent("X"));
    }

}