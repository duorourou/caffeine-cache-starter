package com.github.duorourou.cache.caffeine.configuration.cluster;

import com.github.benmanes.caffeine.cache.RemovalListener;
import org.springframework.cache.CacheManager;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

import java.util.Optional;

import static com.github.duorourou.cache.caffeine.configuration.cluster.ClusterLockCacheKeyUtil.buildLockKey;

@Component
public class RemovalListenerFactory {
    private final ClusterCaffeineSetting clusterSetting;
    private final RemovalMessagePublisher removalMessagePublisher;

    private static final RemovalListener<Object, Object> NO_EARS = (key, value, removalCause) -> {
    };

    public RemovalListenerFactory(@Validated ClusterCaffeineSetting clusterSetting,
                                  RemovalMessagePublisher removalMessagePublisher) {
        this.clusterSetting = clusterSetting;
        this.removalMessagePublisher = removalMessagePublisher;
    }

    /**
     * build a removal listener
     *
     * @param cacheManager
     * @param cache
     * @param clustered
     * @return a removalListener if the cache need to be sync in cluster otherwise an empty listener
     */
    public final RemovalListener<Object, Object> get(CacheManager cacheManager,
                                                     final String cache,
                                                     boolean clustered) {
        return clustered ?
                (k, v, c) ->
                        Optional.ofNullable(cacheManager.getCache(clusterSetting.lockCacheName()))
                                .filter(lockCache -> !lockCache.evictIfPresent(buildLockKey(cache, k)))
                                .ifPresent(lockCache -> removalMessagePublisher.publish(clusterSetting.getPubSubChannel(),
                                        new RemovalMessage(
                                                clusterSetting.getServiceName(),
                                                clusterSetting.getInstanceId(),
                                                cache,
                                                String.valueOf(k))))
                : NO_EARS;
    }

}
