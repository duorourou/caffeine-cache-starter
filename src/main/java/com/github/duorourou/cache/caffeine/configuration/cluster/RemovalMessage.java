package com.github.duorourou.cache.caffeine.configuration.cluster;

import lombok.Value;

@Value
public class RemovalMessage {
    String applicationName;
    String instanceId;
    String cacheKey;
    String cacheName;
}
