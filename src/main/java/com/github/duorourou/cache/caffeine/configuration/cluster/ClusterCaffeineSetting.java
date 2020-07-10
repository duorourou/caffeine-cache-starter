package com.github.duorourou.cache.caffeine.configuration.cluster;

import com.github.benmanes.caffeine.cache.Caffeine;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cache.caffeine.CaffeineCache;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Optional;
import java.util.UUID;

@Getter
@Setter
@Component
@ConfigurationProperties(prefix = "caffeine.cluster", ignoreInvalidFields = true)
public class ClusterCaffeineSetting {
    private final ConfigurableEnvironment configurableEnvironment;

    private String pubSubChannel;
    private LockCache lockCache;
    private String serviceName;
    private String instanceId;

    @Autowired
    public ClusterCaffeineSetting(ConfigurableEnvironment configurableEnvironment) {
        this.configurableEnvironment = configurableEnvironment;
    }


    private LockCache getLockCache() {
        return Optional.ofNullable(lockCache).orElse(new LockCache());
    }

    public CaffeineCache toCache() {
        return lockCache.toCache();
    }

    public String lockCacheName() {
        return this.getLockCache().name;
    }

    @PostConstruct
    public void initialize() {
        Optional.ofNullable(this.instanceId)
                .filter(String::isEmpty)
                .ifPresent(nil -> this.instanceId = Strings.toRootUpperCase(UUID.randomUUID().toString()));
        Optional.ofNullable(this.serviceName)
                .filter(String::isEmpty)
                .ifPresent(nil -> this.serviceName = configurableEnvironment.resolvePlaceholders("${spring.application.name}"));

    }

    @Setter
    @NoArgsConstructor
    static class LockCache {
        private String name = "CUSTOM-CAFFEINE-CACHE-CLUSTER-SYNC-CACHE";
        private String specification = "expireAfterWrite=3s,maximumSize=1000";

        public CaffeineCache toCache() {
            return new CaffeineCache(name, Caffeine.from(specification).build());
        }
    }
}
