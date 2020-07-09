package com.github.duorourou.cache.caffeine.configuration;

import com.github.duorourou.cache.caffeine.annotation.EnableCaffeineCache;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cache.CacheManager;
import org.springframework.test.context.ContextConfiguration;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.notNullValue;

@SpringBootTest(classes = {TestCacheSettingApplication.class})
@ContextConfiguration
@EnableCaffeineCache
class CustomCaffeineCacheConfigurationWithCustomSettingTest {

    @Autowired
    CacheManager cacheManager;

    @Test
    public void could_init_cache_with_custom_settings() {
        assertThat(cacheManager, notNullValue());
        assertThat(cacheManager.getCacheNames(), containsInAnyOrder("X", "Y", "CUSTOM-CAFFEINE-CACHE-CLUSTER-SYNC-CACHE"));
    }

}