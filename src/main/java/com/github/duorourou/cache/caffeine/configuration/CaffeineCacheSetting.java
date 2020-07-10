package com.github.duorourou.cache.caffeine.configuration;

import com.github.benmanes.caffeine.cache.Caffeine;
import com.github.duorourou.cache.caffeine.configuration.cluster.RemovalListenerFactory;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cache.CacheManager;
import org.springframework.cache.caffeine.CaffeineCache;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Setter
@Component
@ConfigurationProperties(prefix = "caffeine.setting", ignoreInvalidFields = true)
public class CaffeineCacheSetting {

    private final RemovalListenerFactory removalListenerFactory;

    private List<CustomCache> caches;

    public CaffeineCacheSetting(RemovalListenerFactory removalListenerFactory) {
        this.removalListenerFactory = removalListenerFactory;
    }


    @Setter
    @NoArgsConstructor
    static class CustomCache {

        private String name;
        private String specification;
        /**
         * A flag that this service is in a cluster group.
         * Then
         * We need set up a message-queue to make the synchronizing-message be published to other nodes.
         */
        private boolean clustered = false;

        public CaffeineCache toCache(CacheManager cacheManager,
                                     RemovalListenerFactory removalListenerFactory) {
            return new CaffeineCache(name,
                    Caffeine.from(specification)
                            .removalListener(removalListenerFactory.get(cacheManager, name, true))
                            .build());
        }
    }


    private List<CustomCache> getCaches() {
        return Optional.ofNullable(caches).orElse(Collections.emptyList());
    }


    public List<CaffeineCache> toCaffeineCaches(CacheManager cacheManager) {
        return getCaches()
                .stream()
                .filter(Objects::nonNull)
                .map(setting -> setting.toCache(cacheManager, removalListenerFactory))
                .collect(Collectors.toList());
    }

}
