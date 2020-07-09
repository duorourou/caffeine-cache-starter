package com.github.duorourou.cache.caffeine.configuration.cluster;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class RemovalListenerFactory {

    private final CacheRemovalListener cacheRemovalListener;
    private static final CacheRemovalListener NO_EARS = (key, value, removalCause) -> {
    };

    public final CacheRemovalListener get(boolean clustered) {
        return clustered ? cacheRemovalListener : NO_EARS;
    }

}
