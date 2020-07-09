package com.github.duorourou.cache.caffeine.configuration;

import com.github.benmanes.caffeine.cache.Caffeine;
import com.github.duorourou.cache.caffeine.configuration.cluster.RemovalListenerFactory;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cache.caffeine.CaffeineCache;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Setter
@Component
@ConfigurationProperties(prefix = "caffeine.setting", ignoreUnknownFields = true, ignoreInvalidFields = true)
public class CaffeineCacheSetting {

    private final RemovalListenerFactory removalListenerFactory;

    private List<CustomCache> caches;

    private LockCache lockCache;

    public CaffeineCacheSetting(RemovalListenerFactory removalListenerFactory) {
        this.removalListenerFactory = removalListenerFactory;
    }


    interface CacheSetting {
        CaffeineCache toCache(RemovalListenerFactory removalListenerFactory);
    }

    @Setter
    @NoArgsConstructor
    static class CustomCache implements CacheSetting {

        private String name;
        private String specification;
        /**
         * A flag that this service is in a cluster group.
         * Then
         * We need set up a message-queue to make the synchronizing-message be published to other nodes.
         */
        private boolean clustered = false;

        public CaffeineCache toCache(RemovalListenerFactory removalListenerFactory) {
            return new CaffeineCache(name,
                    Caffeine.from(specification)
                            .removalListener(removalListenerFactory.get(true))
                            .build());
        }
    }


    @Setter
    @NoArgsConstructor
    static class LockCache implements CacheSetting {
        private String name = "CUSTOM-CAFFEINE-CACHE-CLUSTER-SYNC-CACHE";
        private String specification = "expireAfterWrite=3s,maximumSize=1000";

        public CaffeineCache toCache(RemovalListenerFactory removalListenerFactory) {
            return new CaffeineCache(name, Caffeine.from(specification).build());
        }
    }

    private List<CustomCache> getCaches() {
        return Optional.ofNullable(caches).orElse(Collections.emptyList());
    }

    private LockCache getLockCache() {
        return Optional.ofNullable(lockCache).orElse(new LockCache());
    }

    public List<CaffeineCache> toCaffeineCaches() {
        return Stream.concat(getCaches().stream(), Stream.of(getLockCache()))
                .filter(Objects::nonNull)
                .map(setting -> setting.toCache(removalListenerFactory))
                .collect(Collectors.toList());
    }

}
