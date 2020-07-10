package com.github.duorourou.cache.caffeine.configuration.cluster;

import static org.springframework.util.DigestUtils.md5DigestAsHex;

public class ClusterLockCacheKeyUtil {

    private static final String LOCK_CACHE_KEY_DELIMITER = "|C___K|";

    public static String buildLockKey(String cacheName, Object key) {
        return String.join(LOCK_CACHE_KEY_DELIMITER, cacheName, md5DigestAsHex(String.valueOf(key).getBytes()));
    }

    public static String buildLockKey(RemovalMessage message) {
        return buildLockKey(message.getCacheName(), message.getCacheKey());
    }
}
