package com.github.duorourou.cache.caffeine.configuration;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cache.CacheManager;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.notNullValue;

@SpringBootTest(classes = TestCacheSettingApplication.class)
@ContextConfiguration
@ActiveProfiles("config-file")
class CustomCaffeineCacheConfigurationWithConfigurationFileTest {

    @Autowired
    CacheManager cacheManager;

    @Test
    public void could_init_cache_with_caffeine() {
        assertThat(cacheManager, notNullValue());
    }


}