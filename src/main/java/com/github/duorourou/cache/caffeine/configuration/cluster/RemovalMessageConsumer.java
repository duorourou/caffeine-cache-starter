package com.github.duorourou.cache.caffeine.configuration.cluster;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.CacheManager;
import org.springframework.cache.caffeine.CaffeineCache;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

import java.util.Optional;

import static com.github.duorourou.cache.caffeine.configuration.cluster.ClusterLockCacheKeyUtil.buildLockKey;

/**
 * an implementation of how to consume a removal message from other instance,
 * You can only inject this class in your Subscriber class and call the <code>consume(message)</code> method.
 *
 * e.g. if you use the redis as the pub/sub service. you could create a new Subscriber and deserialize the message
 * from a string the a RemovalMessage then call <code>consume(message)</code> method.
 */
@Component
@Slf4j
public class RemovalMessageConsumer {

    private final ClusterCaffeineSetting clusterCaffeineSetting;
    private final CacheManager cacheManager;
    private final CaffeineCache lockCache;

    public RemovalMessageConsumer(ClusterCaffeineSetting clusterCaffeineSetting,
                                  CacheManager cacheManager,
                                  CaffeineCache lockCache) {
        this.clusterCaffeineSetting = clusterCaffeineSetting;
        this.cacheManager = cacheManager;
        this.lockCache = lockCache;
    }

    /**
     * this is the common implementation, just call this method in **YOUR REAL Subscriber**
     *
     * @param removalMessage removal message
     */
    public void consume(@NonNull RemovalMessage removalMessage) {
        Optional.of(removalMessage)
                .filter(this::fromOtherInstance)
                .filter(this::fromSameService)
                .ifPresent(message -> {
                    Optional.ofNullable(cacheManager.getCache(clusterCaffeineSetting.lockCacheName()))
                            .ifPresent(cache -> {
                                log.debug("invalidate a cache , key : {} in cache {}", message.getCacheKey(), message.getCacheName());
                                lockCache.put(buildLockKey(cache.getName(), message.getCacheKey()), cache.evictIfPresent(message.getCacheKey()));
                            });

                });
    }

    /**
     * make sure the removal message from same service
     * @param message removal message
     * @return true if from same service
     */
    private boolean fromSameService(RemovalMessage message) {
        return clusterCaffeineSetting.getServiceName().equalsIgnoreCase(message.getServiceName());
    }

    /**
     * make sure the message from other instance(s) not from itself
     * @param message removal message
     * @return true if from other instance(s)
     */
    private boolean fromOtherInstance(RemovalMessage message) {
        return !clusterCaffeineSetting.getInstanceId().equalsIgnoreCase(message.getServiceName());
    }

}
